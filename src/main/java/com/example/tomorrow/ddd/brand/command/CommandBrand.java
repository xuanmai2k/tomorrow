package com.example.tomorrow.ddd.brand.command;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommandBrand {
    private String nameBrand;
    private String idCategory;
    private String descriptionBrand;
}
