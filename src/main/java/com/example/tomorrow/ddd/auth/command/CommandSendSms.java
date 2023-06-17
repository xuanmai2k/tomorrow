package com.example.tomorrow.ddd.auth.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CommandSendSms {
    private String phone;
}
