package com.target.myRetail.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.CurrentPrice;
import com.target.myRetail.model.Product;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private MongoDatabase mongoDB;

    @Override
    public CurrentPrice getProductPrice(String productId) throws MyRetailException{
        Document document;
        Double prodValue;
        try {
            // One enhancement, instead of calling DB, we could have cache like Redis or memcache
            // to get the productDetails from cache instead of hitting the DB every time
            final MongoCollection<Document> collection = mongoDB.getCollection("productPrice");
            document = collection.find(eq("_id", productId)).first();
            prodValue = (Double) document.get("price");
        } catch (final Exception e) {
            throw new MyRetailException("Something went wrong while retrieving data from Mongo DB");
        }

        final String currency = (String) document.get("currency");
        final CurrentPrice productPrice = CurrentPrice.builder().value(prodValue)
                .currencyCode(currency).build();
        return productPrice;
    }

    @Override
    public UpdateResult updateProductPrice(String productId, Product product)throws MyRetailException{
        MongoCollection<Document> prodCollection = null;
        Bson filter = null;
        Document document = null;
        JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
        UpdateResult updateResult = null;
        try {
            // One enhancement, instead of calling DB, we could have cache like Redis or memcache
            // to get the productDetails from cache instead of hitting the DB every time
            prodCollection = mongoDB.getCollection("productPrice");
            filter = eq("_id", productId);
            Bson updateOperation = set("price", product.getCurrentPrice().getValue());
            updateResult = prodCollection.updateOne(filter, updateOperation);
            System.out.println("Updating the doc with product id" + productId + ".Updating price to :" + product.getCurrentPrice());
            System.out.println(updateResult);
            System.out.println(prodCollection.find(filter).first().toJson(prettyPrint));
            updateResult.getModifiedCount();

        } catch (final Exception e) {
            throw new MyRetailException("Something went wrong while retrieving data from Mongo DB");
        }
        return updateResult;
    }
}
