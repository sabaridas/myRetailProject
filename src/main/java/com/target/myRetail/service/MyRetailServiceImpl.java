package com.target.myRetail.service;

import com.mongodb.client.result.UpdateResult;
import com.target.myRetail.dao.ProductDao;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.CurrentPrice;
import com.target.myRetail.model.Product;
import com.target.myRetail.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyRetailServiceImpl implements MyRetailService {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductDao productDao;

    @Override
    public Optional<Product> getProduct(String productId) throws MyRetailException {
        CurrentPrice prodPrice = null;
        //retrieve product details from downstream
        ProductDetails prodDetails = productDetailService.getProductDetails(productId);
        if(prodDetails != null){
            //retrieve price details from MongoDB
            prodPrice = productDao.getProductPrice(productId);
        } else {
            throw new MyRetailException("Product details not found while calling downstream");
        }

        //combine product and price details
        if (prodDetails != null && prodDetails.getId() != null && prodDetails.getName() != null
                && prodPrice != null) {
            return Optional.of( Product.builder().id(prodDetails.getId()).name(prodDetails.getName())
                    .currentPrice(prodPrice).build());
        }
        return Optional.empty();
    }

    @Override
    public UpdateResult updateProduct(String productId,Product product) throws MyRetailException {
        return productDao.updateProductPrice(productId,product);
    }
}
