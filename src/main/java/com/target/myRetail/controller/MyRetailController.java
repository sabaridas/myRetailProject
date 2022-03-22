package com.target.myRetail.controller;

import com.mongodb.client.result.UpdateResult;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.Product;
import com.target.myRetail.service.MyRetailService;
import com.target.myRetail.service.MyRetailServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("myRetail")
public class MyRetailController {

    @Autowired
    private MyRetailService myRetailService;

    Logger logger = LoggerFactory.getLogger(MyRetailController.class);

    @GetMapping(path ="/product/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
   public ResponseEntity<Product> getProduct(@PathVariable("id") String productId) throws MyRetailException {
        Optional<Product> product = myRetailService.getProduct(productId);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path ="/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String productId, @RequestBody Product product) throws MyRetailException {
        UpdateResult updatedProd = myRetailService.updateProduct(productId,product);
        if(updatedProd.getModifiedCount() != 0) {
            return new ResponseEntity("Price successfully updated", HttpStatus.OK);
        } else if(updatedProd.getUpsertedId() != null){
            return new ResponseEntity("New record inserted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Record not found/updated", HttpStatus.BAD_REQUEST);
        }
    }

}
