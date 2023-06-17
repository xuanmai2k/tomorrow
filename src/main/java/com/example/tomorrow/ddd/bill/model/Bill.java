package com.example.tomorrow.ddd.bill.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document(collection = "bill")
public class Bill implements Serializable {
    @Id
    @JsonSerialize (using = ToStringSerializer.class)
    ObjectId _id;
    private String idUser;
    private List<BillingDetail> billingDetails;
    @Builder.Default
    private Boolean isDelete = false;
    private Long createdAt;
}