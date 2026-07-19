package com.catchstyle.aca.common.config;

import com.catchstyle.aca.post.domain.Category;
import com.catchstyle.aca.post.domain.LinkType;
import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.post.domain.Product;
import com.catchstyle.aca.post.domain.ScheduleType;
import com.catchstyle.aca.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DummyDataInitializerTest {

    private static final String KARINA_POST_URL =
            "https://www.instagram.com/p/DW_C_l3FA1k/?img_index=1";

    @Mock
    private PostRepository postRepository;

    @Test
    void updatesExistingDummyDataImagePathsToPng() {
        Post existingPost = Post.builder()
                .celebName("카리나")
                .groupName("에스파")
                .postDate(LocalDate.of(2026, 4, 11))
                .scheduleType(ScheduleType.INSTA)
                .linkType(LinkType.INSTA)
                .linkUrl(KARINA_POST_URL)
                .outfitImageUrl("/images/outfits/260411-karina.jpg")
                .build();
        existingPost.addProduct(Product.builder()
                .category(Category.DRESS)
                .brandName("신스덴")
                .price(169_000L)
                .productUrl("https://sincethen.co.kr/product/프로미스-벌룬-미니원피스/4058/")
                .productImageUrl("/images/products/260411-karina-01.jpg")
                .build());

        when(postRepository.findByCelebNameAndLinkUrl(anyString(), anyString()))
                .thenAnswer(invocation -> {
                    String celebName = invocation.getArgument(0);
                    String linkUrl = invocation.getArgument(1);
                    if ("카리나".equals(celebName) && KARINA_POST_URL.equals(linkUrl)) {
                        return Optional.of(existingPost);
                    }
                    return Optional.empty();
                });

        new DummyDataInitializer(postRepository).run(null);

        assertThat(existingPost.getOutfitImageUrl())
                .isEqualTo("/images/outfits/260411-karina.png");
        assertThat(existingPost.getProducts())
                .singleElement()
                .extracting(Product::getProductImageUrl)
                .isEqualTo("/images/products/260411-karina-01.png");
    }
}
