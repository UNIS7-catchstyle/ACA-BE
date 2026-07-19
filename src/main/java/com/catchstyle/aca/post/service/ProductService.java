package com.catchstyle.aca.post.service;

import com.catchstyle.aca.common.exception.CustomException;
import com.catchstyle.aca.common.exception.ErrorCode;
import com.catchstyle.aca.post.domain.Product;
import com.catchstyle.aca.post.dto.ProductDto;
import com.catchstyle.aca.post.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final InstagramService instagramService; //데모용 상품 썸네일 스크래퍼
    private final ProductImageService productImageService;

    //상품 정보 수정
    @Transactional
    public void updateProduct(Long productId, ProductDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        String imageUrl = getImageUrl(request.productUrl(), request.productImageUrl());

        product.update(
                request.category(),
                request.brandName(),
                request.productUrl(),
                imageUrl, // 가공된 URL 적용
                request.price()
        );
    }

    private String getImageUrl(String productUrl, String currentImageUrl) {
        if (currentImageUrl != null && !currentImageUrl.isBlank()) {
            return currentImageUrl;
        }

        if (productUrl == null || productUrl.isBlank()) return null;

        if (productUrl.contains("instagram.com")) {
            return instagramService.getThumbnailUrl(productUrl);
        } else {
            return productImageService.getProductThumbnailUrl(productUrl);
        }
    }
}
