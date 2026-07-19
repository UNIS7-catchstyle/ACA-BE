package com.catchstyle.aca.post.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MVP에서는 작성자 id 대신 1L 등의 임의 더미 값을 세팅.
    @Column(nullable = false)
    @Builder.Default
    private Long userId = 1L;

    //연예인 이름
    @Column(nullable = false)
    private String celebName;

    //그룹명(선택)
    private String groupName;

    //일정 타입
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // 이거 안하면 JPA가 기본값인 숫자인덱스로 저장. 나중에 항목 바뀔 시 문제 생김
    private ScheduleType scheduleType;

    //착장 공개 날짜
    @Column(nullable = false)
    private LocalDate postDate;

    // 착장 이미지 URL
    @Column(length = 1000)
    private String outfitImageUrl;

    //착장글 링크
    @Column(nullable = false)
    private String linkUrl;

    //착장 링크 타입
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkType linkType; // YOUTUBE, INSTA...

    // Post가 저장/삭제될 때 연관된 Clothes도 자동으로 함께 저장/삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();


    // 양방향 연관관계 편의 메서드 (하나의 트랜잭션 내에서 양쪽 상태를 동기화하기 위함)
    public void addProduct(Product product) {
        this.products.add(product);
        product.assignPost(this);
    }
}
