package com.example.tomorrow.ddd.user;

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
@Document(collection = "user")
public class User implements Serializable { //doc of mongo
    @Id
    @JsonSerialize(using = ToStringSerializer.class)//chuyển từ objectID sang String
    ObjectId _id;
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String email;
    @Builder.Default
    private  Boolean isDelete = false;
    private Long createdAt;

}
