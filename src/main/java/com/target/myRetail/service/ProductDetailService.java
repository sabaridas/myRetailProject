package com.target.myRetail.service;

import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.ProductDetails;


public interface ProductDetailService {
    public ProductDetails getProductDetails(String productId) throws MyRetailException;
}
