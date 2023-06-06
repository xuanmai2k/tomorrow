package com.example.tomorrow.ddd.category.command;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommandCategory {
    private String nameCategory;
    private String descriptionCategory;
}
