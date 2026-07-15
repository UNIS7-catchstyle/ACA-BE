package com.catchstyle.aca.common.util;

import com.catchstyle.aca.post.domain.Post;

import java.time.format.DateTimeFormatter;

public final class TitleFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private TitleFormatter() {
    }

    public static String format(Post post) {
        return "%s %s %s %s".formatted(
                post.getPostDate().format(DATE_FORMATTER),
                post.getGroupName(),
                post.getCelebName(),
                post.getTagName()
        );
    }
}