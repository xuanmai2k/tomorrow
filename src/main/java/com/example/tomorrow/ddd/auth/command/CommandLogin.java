package com.example.tomorrow.ddd.auth.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandLogin {
    private String Phone;
    private String password;
}
