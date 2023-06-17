package com.example.tomorrow.ddd.auth.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TokenInfo {
    private String userName;
    private String name;
    private String role;
}
