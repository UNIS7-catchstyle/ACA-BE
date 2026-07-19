package com.catchstyle.aca.post.service;

import com.catchstyle.aca.common.exception.CustomException;
import com.catchstyle.aca.common.exception.ErrorCode;
import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.post.dto.PostDetailResponse;
import com.catchstyle.aca.post.dto.PostListResponse;
import com.catchstyle.aca.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catchstyle.aca.post.domain.Product;
import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.post.dto.ProductDto;
import com.catchstyle.aca.post.dto.PostDto;
import com.catchstyle.aca.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostListResponse getPosts(String keyword, Pageable pageable) {
        Page<Post> page = (keyword == null || keyword.isBlank())
                ? postRepository.findAll(pageable)
                : postRepository.searchByKeyword(keyword.trim(), pageable);
        return PostListResponse.from(page);
    }

    public PostDetailResponse getPost(Long postId) {
        if (postId == null || postId <= 0) {
            throw new CustomException(ErrorCode.INVALID_POST_ID);
        }
        Post post = postRepository.findWithProductsById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return PostDetailResponse.from(post);
    }
}
    private final PostRepository postRepository;


    @Transactional
    public Long createPost(PostDto request){
        Post post = Post.builder()
                .celebName(request.celebName())
                .groupName(request.groupName())
                .scheduleType(request.scheduleType())
                .postDate(request.postDate())
                .outfitImageUrl(request.outfitImageUrl())
                .linkUrl(request.link().url())
                .linkType(request.link().type())
                // Builder.Default 설정으로 products 리스트는 자동 초기화됨
                .build();

        if(request.products()!=null){
            //for (타입 변수명 : 반복할 데이터 모음)
            for (ProductDto productDto : request.products()) {
                Product product = Product.builder()
                        .category(productDto.category())
                        .brandName(productDto.brandName())
                        .productUrl(productDto.productUrl())
                        .productImageUrl(productDto.productImageUrl())
                        .price(productDto.price())
                        .build();
                post.addProduct(product);
            }
        }

        Post savedPost=postRepository.save(post);
        return savedPost.getId();
    }

    //특정 게시글에 상품 정보 1건 추가
    @Transactional
    public void addProductToPost(Long postId, ProductDto request) {
        // 1. 게시글 찾기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 2. 단일 상품 정보 생성
        Product product = Product.builder()
                .category(request.category())
                .brandName(request.brandName())
                .productUrl(request.productUrl())
                .productImageUrl(request.productImageUrl())
                .price(request.price())
                .build();

        // 3. 게시글에 상품 추가 (Cascade 설정으로 인해 자동 저장됨)
        post.addProduct(product);
    }
}
