package com.catchstyle.aca.post.controller;

import com.catchstyle.aca.common.response.ApiResponse;
import com.catchstyle.aca.post.dto.ProductDto;
import com.catchstyle.aca.post.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 정보 수정
    @PutMapping("/{productId}")
    public ApiResponse<Void> updateProduct(
            @PathVariable Long productId,
            @RequestBody @Valid ProductDto request
    ) {
        productService.updateProduct(productId, request);
        return ApiResponse.success(200, "상품 정보 수정 성공", null);
    }
}
