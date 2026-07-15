package com.catchstyle.aca.post.controller;


import com.catchstyle.aca.common.response.ApiResponse;
import com.catchstyle.aca.post.dto.PostDto;
import com.catchstyle.aca.post.dto.ProductDto;
import com.catchstyle.aca.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시물 및 옷 정보 등록
    @PostMapping
    public ApiResponse<Map<String, Long>> createPost(@RequestBody @Valid PostDto request) {
        Long postId = postService.createPost(request);

        return ApiResponse.success(200, "게시글 등록 성공", Map.of("postId", postId));
    }

    //옷 정보 등록
    @PostMapping("/{postId}")
    public ApiResponse<Void> addProductToPost(
            @PathVariable Long postId,
            @RequestBody @Valid ProductDto request) {
        postService.addProductToPost(postId,request);

        return ApiResponse.success(200, "상품 등록 성공", null);
    }




}
