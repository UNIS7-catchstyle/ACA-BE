package com.catchstyle.aca.post.dto;


import com.catchstyle.aca.post.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
        @NotNull(message = "카테고리는 필수입니다.")
        Category category,

        @NotBlank(message = "브랜드명은 필수입니다.")
        String brandName,

        String productUrl,

        String productImageUrl,

        @NotNull(message = "가격은 필수입니다.")
        Long price
){}
