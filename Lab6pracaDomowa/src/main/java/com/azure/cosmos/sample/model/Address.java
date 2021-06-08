package com.azure.cosmos.sample.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Address {
    private String street;
    private int houseNumber;
    private int flatNumber;
    private String postcode;
}
