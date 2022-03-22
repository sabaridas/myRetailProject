package com.target.myRetail.dao;

import com.mongodb.client.result.UpdateResult;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.CurrentPrice;
import com.target.myRetail.model.Product;

public interface ProductDao {
    CurrentPrice getProductPrice(String productId) throws MyRetailException;
    UpdateResult updateProductPrice(String productId, Product product)throws MyRetailException;
}
