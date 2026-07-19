package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.post.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostListResponse(
        List<PostListItemResponse> posts,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

    public static PostListResponse from(Page<Post> page) {
        List<PostListItemResponse> items = page.getContent().stream()
                .map(PostListItemResponse::from)
                .toList();

        return new PostListResponse(
                items,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}
