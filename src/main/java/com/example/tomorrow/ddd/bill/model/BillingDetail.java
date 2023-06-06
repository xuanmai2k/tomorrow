package com.example.tomorrow.ddd.bill.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BillingDetail {
    private String idProduct;
    private Integer priceProduct;
    private String description;
}
