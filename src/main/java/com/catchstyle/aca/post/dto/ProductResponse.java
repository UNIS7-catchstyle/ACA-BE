package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.post.domain.Category;
import com.catchstyle.aca.post.domain.Product;

public record ProductResponse(
        Long productId,
        String productImageUrl,
        String brandName,
        Category category,
        Long price
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductImageUrl(),
                product.getBrandName(),
                product.getCategory(),
                product.getPrice()
        );
    }
}
