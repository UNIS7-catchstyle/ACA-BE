package com.catchstyle.aca.post.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {

    public String getProductThumbnailUrl(String productUrl) {
        try {
            // 해당 상품 페이지 접속
            Document doc = Jsoup.connect(productUrl).get();
            // og:image 태그의 content 속성 가져오기
            return doc.select("meta[property=og:image]").first().attr("content");
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 실패 시 null 반환
        }
    }
}
