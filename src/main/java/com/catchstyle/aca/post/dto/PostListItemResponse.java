package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.common.util.TitleFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PostListItemResponse(
        Long postId,
        String title,
        String outfitImageUrl,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate postDate,
        String groupName,
        String celebName,
        String tagName
) {

    public static PostListItemResponse from(Post post) {
        return new PostListItemResponse(
                post.getId(),
                TitleFormatter.format(post),
                post.getOutfitImageUrl(),
                post.getPostDate(),
                post.getGroupName(),
                post.getCelebName(),
                post.getTagName()
        );
    }
}
