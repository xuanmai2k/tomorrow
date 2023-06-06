package com.example.tomorrow.ddd.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Document( collection = "product")
public class Product implements Serializable {
    @Id
    @JsonSerialize (using = ToStringSerializer.class)
    ObjectId _id;
    private String nameProduct;
    private Integer priceProduct;
    private String idCategory;
    private String idBrand;
    private String descriptionProduct;
    private String urlImageProduct;
    private Integer numInventory;
    @Builder.Default
    private Boolean isDelete = false;
    private Long createdAt;

}
