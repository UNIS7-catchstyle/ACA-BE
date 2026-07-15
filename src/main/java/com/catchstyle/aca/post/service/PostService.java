package com.catchstyle.aca.post.service;

import com.catchstyle.aca.common.exception.CustomException;
import com.catchstyle.aca.common.exception.ErrorCode;
import com.catchstyle.aca.post.domain.Post;
import com.catchstyle.aca.post.dto.PostDetailResponse;
import com.catchstyle.aca.post.dto.PostListResponse;
import com.catchstyle.aca.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostListResponse getPosts(String keyword, Pageable pageable) {
        Page<Post> page = (keyword == null || keyword.isBlank())
                ? postRepository.findAll(pageable)
                : postRepository.searchByKeyword(keyword.trim(), pageable);
        return PostListResponse.from(page);
    }

    public PostDetailResponse getPost(Long postId) {
        if (postId == null || postId <= 0) {
            throw new CustomException(ErrorCode.INVALID_POST_ID);
        }
        Post post = postRepository.findWithProductsById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return PostDetailResponse.from(post);
    }
}