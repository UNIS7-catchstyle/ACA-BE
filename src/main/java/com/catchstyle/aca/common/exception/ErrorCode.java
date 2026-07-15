package com.catchstyle.aca.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "게시글을 찾을 수 없습니다."),
    INVALID_POST_ID(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 게시글 ID입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상품을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
