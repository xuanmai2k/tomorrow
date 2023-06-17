package com.example.tomorrow.ddd.bill.command;

import com.example.tomorrow.ddd.bill.model.BillingDetail;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommandBill {
    private String idUser;
    private List<BillingDetail> billingDetails;
}


