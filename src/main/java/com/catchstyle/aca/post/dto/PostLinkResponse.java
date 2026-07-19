package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.post.domain.LinkType;
import com.catchstyle.aca.post.domain.Post;

public record PostLinkResponse(String url, LinkType type) {

    public static PostLinkResponse from(Post post) {
        if (post.getLinkUrl() == null && post.getLinkType() == null) {
            return null;
        }
        return new PostLinkResponse(post.getLinkUrl(), post.getLinkType());
    }
}
