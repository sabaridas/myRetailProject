package com.target.myRetail.controller;

import com.target.myRetail.model.CurrentPrice;
import com.target.myRetail.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyRetailController.class)
public class MyRetailControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MyRetailController myRetailController;

    @Test
    public void getProduct_success() throws Exception {
        Product product = Product.builder().id("13860428").name("The Big Lebowski (Blu-ray) (Widescreen)")
                .currentPrice(CurrentPrice.builder().value(13.49d).currencyCode("USD").build()).build();;

        Mockito.when(myRetailController.getProduct(any(String.class)))
                .thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/myRetail/product/13860428")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("The Big Lebowski (Blu-ray) (Widescreen)")));
    }

    @Test
    public void getProduct_failure() throws Exception {
        Mockito.when(myRetailController.getProduct(any(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/myRetail/product/13860429")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
