package com.example.tomorrow.ddd.auth.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandRegister {
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String email;
}
