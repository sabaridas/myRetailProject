package com.target.myRetail.service;

import com.mongodb.client.result.UpdateResult;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.Product;

import java.util.Optional;


public interface MyRetailService {
    Optional<Product> getProduct(String productId) throws MyRetailException;
    UpdateResult updateProduct(String productId, Product product) throws MyRetailException;

}
