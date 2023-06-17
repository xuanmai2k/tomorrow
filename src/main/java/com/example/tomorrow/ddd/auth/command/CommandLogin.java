package com.example.tomorrow.ddd.auth.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandLogin {
    private String userName;
    private String password;
}
