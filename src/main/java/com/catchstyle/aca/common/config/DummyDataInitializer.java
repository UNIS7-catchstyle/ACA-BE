package com.catchstyle.aca.common.config;

import com.catchstyle.aca.post.domain.*;
import com.catchstyle.aca.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "app.dummy-data",
        name = "enabled",
        havingValue = "true"
)
public class DummyDataInitializer implements ApplicationRunner {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        upsert(
                "카리나",
                "에스파",
                LocalDate.of(2026, 4, 11),
                ScheduleType.INSTA,
                LinkType.INSTA,
                "https://www.instagram.com/p/DW_C_l3FA1k/?img_index=1",
                "/images/outfits/260411-karina.png",
                List.of(
                        product(
                                Category.DRESS,
                                "신스덴",
                                169_000L,
                                "https://sincethen.co.kr/product/프로미스-벌룬-미니원피스/4058/",
                                "/images/products/260411-karina-01.png"
                        )
                )
        );

        upsert(
                "카리나",
                "에스파",
                LocalDate.of(2026, 2, 18),
                ScheduleType.INSTA,
                LinkType.INSTA,
                "https://www.instagram.com/p/DUu5AF1ko76/?img_index=1",
                "/images/outfits/260218-karina.png",
                List.of(
                        product(
                                Category.TOP,
                                "리포메이션 Julie Knit Top",
                                128_600L,
                                "https://www.thereformation.com/products/julie-knit-top/1317228.html",
                                "/images/products/260218-karina-01.png"
                        ),
                        product(
                                Category.ACCESSORY,
                                "까르띠에 탱크 머스트",
                                6_000_000L,
                                "https://www.cartier.com/ko-kr/watches/all-collections/tank/",
                                "/images/products/260218-karina-02.png"
                        ),
                        product(
                                Category.ACCESSORY,
                                "젠틀몬스터 노이드 01",
                                280_000L,
                                "https://www.gentlemonster.com/kr/ko/item/0NA26NCES71DM/noid01",
                                "/images/products/260218-karina-03.png"
                        )
                )
        );

        upsert(
                "카리나",
                "에스파",
                LocalDate.of(2026, 4, 27),
                ScheduleType.INSTA,
                LinkType.INSTA,
                "https://www.instagram.com/p/DXogIqrFINU/?img_index=1",
                "/images/outfits/260427-karina.png",
                List.of(
                        product(
                                Category.DRESS,
                                "팽 시스템 (Feng System)",
                                105_000L,
                                "https://www.fengsystem.co/products/a1-tan-playsuit",
                                "/images/products/260427-karina-01.png"
                        )
                )
        );

        upsert(
                "윈터",
                "에스파",
                LocalDate.of(2026, 3, 8),
                ScheduleType.INSTA,
                LinkType.INSTA,
                "https://www.instagram.com/p/DXogIqrFINU/?img_index=1",
                "/images/outfits/260308-winter.png",
                List.of(
                        product(
                                Category.OUTER,
                                "엔니즈 (ANDNEEDS)",
                                120_000L,
                                "https://www.29cm.co.kr/products/3051060",
                                "/images/products/260308-winter-01.png"
                        )
                )
        );

        upsert(
                "닝닝",
                "에스파",
                LocalDate.of(2025, 10, 8),
                ScheduleType.YOUTUBE,
                LinkType.YOUTUBE,
                "https://www.youtube.com/watch?v=NQwmhE_Gakc",
                "/images/outfits/251008-NingNing.png",
                List.of(
                        product(
                                Category.OUTER,
                                "이지 (Yeezy)",
                                56_000L,
                                "https://kream.co.kr/products/562101",
                                "/images/products/251008-NingNing-01.png"
                        )
                )
        );
    }

    private void upsert(
            String celebName,
            String groupName,
            LocalDate postDate,
            ScheduleType scheduleType,
            LinkType linkType,
            String linkUrl,
            String outfitImageUrl,
            List<Product> products
    ) {
        Post post = postRepository.findByCelebNameAndLinkUrl(celebName, linkUrl)
                .orElseGet(() -> Post.builder()
                        .celebName(celebName)
                        .groupName(groupName)
                        .postDate(postDate)
                        .scheduleType(scheduleType)
                        .linkType(linkType)
                        .linkUrl(linkUrl)
                        .outfitImageUrl(outfitImageUrl)
                        .build());

        post.update(
                celebName,
                groupName,
                scheduleType,
                postDate,
                outfitImageUrl,
                linkUrl,
                linkType
        );
        syncProducts(post, products);
        postRepository.save(post);
    }

    private void syncProducts(Post post, List<Product> desiredProducts) {
        List<Product> unmatchedProducts = new ArrayList<>(post.getProducts());

        for (Product desired : desiredProducts) {
            Product existing = unmatchedProducts.stream()
                    .filter(product -> Objects.equals(
                            product.getProductUrl(),
                            desired.getProductUrl()
                    ))
                    .findFirst()
                    .orElse(null);

            if (existing == null) {
                post.addProduct(desired);
                continue;
            }

            existing.update(
                    desired.getCategory(),
                    desired.getBrandName(),
                    desired.getProductUrl(),
                    desired.getProductImageUrl(),
                    desired.getPrice()
            );
            unmatchedProducts.remove(existing);
        }

        unmatchedProducts.forEach(post::removeProduct);
    }

    private Product product(
            Category category,
            String brandName,
            Long price,
            String productUrl,
            String productImageUrl
    ) {
        return Product.builder()
                .category(category)
                .brandName(brandName)
                .price(price)
                .productUrl(productUrl)
                .productImageUrl(productImageUrl)
                .build();
    }
}
