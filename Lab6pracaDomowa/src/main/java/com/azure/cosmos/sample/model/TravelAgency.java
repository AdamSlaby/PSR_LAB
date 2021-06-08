package com.azure.cosmos.sample.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TravelAgency {
    private String id;
    private String partitionKey;
    private String name;
    private Address address;
}
