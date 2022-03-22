package com.target.myRetail.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrentPrice {
    Double value;
    String currencyCode;
}
