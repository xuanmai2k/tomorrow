package com.example.tomorrow.ddd.user.command;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommandChangePassword {
    private String newPassword;
    private String phone;
    private String oldPassword;
}
