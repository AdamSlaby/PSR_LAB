package com.azure.cosmos.sample.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response {
    @JsonSerialize
    float price;

    @JsonCreator
    public Response(@JsonProperty("price") float price) {
        this.price = price;
    }
}
