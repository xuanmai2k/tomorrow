package com.example.tomorrow.ddd.brand;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document (collection = "brand")
public class Brand implements Serializable {
    @Id
    @JsonSerialize (using = ToStringSerializer.class)
    ObjectId _id;
    private String nameBrand;
    private String idCategory;
    private String descriptionBrand;
    @Builder.Default
    private Boolean isDelete = false;
    private Long createdAt;
}
