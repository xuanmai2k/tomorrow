package com.example.tomorrow.ddd.user.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandUser { //chứa params input từ web
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String email;
}
