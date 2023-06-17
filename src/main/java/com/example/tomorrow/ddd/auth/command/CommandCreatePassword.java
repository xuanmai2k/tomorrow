package com.example.tomorrow.ddd.auth.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandCreatePassword {
    private String password;
    private String phone;
}
