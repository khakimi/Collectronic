package com.example.collectronic.repository;

import com.example.collectronic.entity.Comment;
import com.example.collectronic.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItem(Item item);
    Comment findByIdAndUserId(Long commentId, Long userId);
}
