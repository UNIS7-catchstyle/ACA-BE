package com.catchstyle.aca.post.service;

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

    Long dummyUserId=1L;

    @Transactional
    public Long createPost(PostDto request){
        Post post = Post.builder()
                .celebName(request.celebName())
                .groupName(request.groupName())
                .tagName(request.tagName())
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
}
