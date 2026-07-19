package com.catchstyle.aca.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

//데모용 인스타 이미지 스크래퍼
@Service
@RequiredArgsConstructor
public class InstagramService {
    private final RestTemplate restTemplate;

    @Value("${SCRAPPER_API_KEY}")
    private String apiKey;

    public String getThumbnailUrl(String url) {
        if (!url.contains("instagram.com")) return null;

        String apiUrl = "https://api.linkpreview.net?key=" + apiKey + "&q=" + url;

        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("image")) {
                return (String) response.get("image");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
