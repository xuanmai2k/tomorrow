package com.example.tomorrow.ddd.product.command;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommandProduct {
    private String nameProduct;
    private Integer priceProduct;
    private String idCategory;
    private String idBrand;
    private String descriptionProduct;
    private String urlImageProduct;
    private Integer numInventory;
}
