package com.revature.revshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String skuCode;
    private CategoryDto categoryDto;

}
