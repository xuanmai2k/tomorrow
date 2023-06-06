package com.example.tomorrow.ddd.bill.command;

import com.example.tomorrow.ddd.bill.model.BillingDetail;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommandBill {
    private String idUser;
    private BillingDetail billingDetail;
}


