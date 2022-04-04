package com.example.CodeFellowshipPro.repositories;

import com.example.CodeFellowshipPro.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Post findPostById(Long post_id);
}
