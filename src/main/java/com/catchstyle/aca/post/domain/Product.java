package com.catchstyle.aca.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, length = 100)
    private String brandName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Column(nullable = false)
    private Long price;

    @Column(length = 1000)
    private String productImageUrl;

    @Column(length = 1000)
    private String productUrl;

    // 양방향 연관관계 편의 메서드용 (package-private)
    void assignPost(Post post) {
        this.post = post;
    }

    public Product(Category category, String brandName, String productUrl,String productImageUrl, Long price) {
        this.category = category;
        this.brandName = brandName;
        this.productUrl = productUrl;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }


    // 상품 정보 수정 시 자동 업데이트
    public void update(Category category, String brandName, String productUrl,String productImageUrl, Long price) {
        this.category = category;
        this.brandName = brandName;
        this.productUrl = productUrl;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }

}
