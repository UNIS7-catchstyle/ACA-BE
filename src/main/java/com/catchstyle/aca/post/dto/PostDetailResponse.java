package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.common.util.TitleFormatter;
import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.post.domain.ScheduleType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record PostDetailResponse(
        Long postId,
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate postDate,
        String groupName,
        String celebName,
        ScheduleType scheduleType,
        PostLinkResponse link,
        List<ProductResponse> products
) {
    public static PostDetailResponse from(Post post) {
        List<ProductResponse> products = post.getProducts().stream()
                .map(ProductResponse::from)
                .toList();

        return new PostDetailResponse(
                post.getId(),
                TitleFormatter.format(post),
                post.getPostDate(),
                post.getGroupName(),
                post.getCelebName(),
                post.getScheduleType(),
                PostLinkResponse.from(post),
                products
        );
    }
}