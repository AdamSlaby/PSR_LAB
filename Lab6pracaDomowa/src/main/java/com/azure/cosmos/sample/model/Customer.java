package com.azure.cosmos.sample.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Customer {
    private String id;
    private String partitionKey;
    private String name;
    private String surname;
    private int age;
    private Address address;
}
