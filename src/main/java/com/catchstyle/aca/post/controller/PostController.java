package com.catchstyle.aca.post.controller;

import com.catchstyle.aca.common.response.ApiResponse;
import com.catchstyle.aca.post.dto.PostDetailResponse;
import com.catchstyle.aca.post.dto.PostDto;
import com.catchstyle.aca.post.dto.PostListResponse;
import com.catchstyle.aca.post.dto.ProductDto;
import com.catchstyle.aca.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<PostListResponse> getPosts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 10, sort = "postDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PostListResponse data = postService.getPosts(keyword, pageable);
        String message = (keyword == null || keyword.isBlank())
                ? "게시글 전체 조회 성공"
                : "게시글 검색 성공";
        return ApiResponse.success(200, message, data);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse data = postService.getPost(postId);
        return ApiResponse.success(200, "게시글 상세 조회 성공", data);
    }
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
