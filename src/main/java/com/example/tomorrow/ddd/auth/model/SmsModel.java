package com.example.tomorrow.ddd.auth.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SmsModel {
    private String apiKey; // why?
    private String content;
    private String phone;
    private String secretKey;// why?
}
