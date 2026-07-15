package com.catchstyle.aca.post.dto;

import com.catchstyle.aca.post.domain.LinkType;
import com.catchstyle.aca.post.domain.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

//record는 불변객체. 생성자,getter 자동빌드/Lombok 어노테이션 필요없음(심플)
public record PostDto(
        @NotBlank(message = "연예인 이름은 필수입니다.")
        String celebName,

        String groupName,

        @NotBlank(message = "일정 태그는 필수입니다.")
        ScheduleType scheduleType,

        @NotNull(message = "착장 공개 날짜는 필수입니다.")
        LocalDate postDate,

        String outfitImageUrl,

        @NotNull(message = "링크 정보는 필수입니다.")
        LinkRequest link,

        List<ProductDto> products // clothesList -> products 변경
) {
    //중첩 구조 JSON 매핑을 위한 내부 record
    public record LinkRequest(
            @NotBlank(message = "링크 URL은 필수입니다.")
            String url,

            @NotNull(message = "링크 타입은 필수입니다.")
            LinkType type
    ) {}
}
