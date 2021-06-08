package com.azure.cosmos.sample.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Voyage {
    private String id;
    private String partitionKey;
    private String destination;
    private float price;
    private int days;
}
