package com.catchstyle.aca.post.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false)
    private String celebName;

    private String groupName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @Column(nullable = false)
    private LocalDate postDate;

    @Column(length = 1000)
    private String outfitImageUrl;

    @Column(nullable = false)
    private String linkUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkType linkType;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();


    // 양방향 연관관계 편의 메서드 (하나의 트랜잭션 내에서 양쪽 상태를 동기화하기 위함)
    public void addProduct(Product product) {
        this.products.add(product);
        product.assignPost(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void update(
            String celebName,
            String groupName,
            ScheduleType scheduleType,
            LocalDate postDate,
            String outfitImageUrl,
            String linkUrl,
            LinkType linkType
    ) {
        this.celebName = celebName;
        this.groupName = groupName;
        this.scheduleType = scheduleType;
        this.postDate = postDate;
        this.outfitImageUrl = outfitImageUrl;
        this.linkUrl = linkUrl;
        this.linkType = linkType;
    }
}
