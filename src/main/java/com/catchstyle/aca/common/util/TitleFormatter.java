package com.catchstyle.aca.common.util;

import com.catchstyle.aca.post.domain.Post;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class TitleFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private TitleFormatter() {
    }

    public static String format(Post post) {
        List<String> parts = new ArrayList<>();

        parts.add(post.getPostDate().format(DATE_FORMATTER));

        if (post.getGroupName() != null && !post.getGroupName().isBlank()) {
            parts.add(post.getGroupName());
        }

        parts.add(post.getCelebName());
        parts.add(post.getScheduleType().name());

        return String.join(" ", parts);
    }
}