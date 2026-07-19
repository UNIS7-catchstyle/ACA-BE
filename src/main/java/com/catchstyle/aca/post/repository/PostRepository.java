package com.catchstyle.aca.post.repository;

import com.catchstyle.aca.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "products")
    Optional<Post> findWithProductsById(Long id);

    @Query(
            value = """
            SELECT p
            FROM Post p
            WHERE LOWER(CONCAT(
                FUNCTION('DATE_FORMAT', p.postDate, '%y%m%d'),
                CASE
                    WHEN p.groupName IS NULL OR TRIM(p.groupName) = ''
                    THEN ''
                    ELSE CONCAT(' ', p.groupName)
                END,
                ' ',
                p.celebName,
                ' ',
                CAST(p.scheduleType AS string)
            )) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """,
            countQuery = """
            SELECT COUNT(p)
            FROM Post p
            WHERE LOWER(CONCAT(
                FUNCTION('DATE_FORMAT', p.postDate, '%y%m%d'),
                CASE
                    WHEN p.groupName IS NULL OR TRIM(p.groupName) = ''
                    THEN ''
                    ELSE CONCAT(' ', p.groupName)
                END,
                ' ',
                p.celebName,
                ' ',
                CAST(p.scheduleType AS string)
            )) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """
    )
    Page<Post> searchByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
