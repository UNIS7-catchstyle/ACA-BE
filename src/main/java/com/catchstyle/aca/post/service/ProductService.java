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
    private final InstagramService instagramService; //데모용 인스타 썸네일 스크래퍼

    //상품 정보 수정
    @Transactional
    public void updateProduct(Long productId, ProductDto request) {
        // 1. 수정할 상품 조회 (혹시 존재하지 않을 시 예외 처리)
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // 2. 수정사항 업데이트
        product.update(
                request.category(),
                request.brandName(),
                request.productUrl(),
                request.productImageUrl(),
                request.price()
        );
    }
}
