package com.target.myRetail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {
    private String id;
    private String name;
    private CurrentPrice currentPrice;
}

