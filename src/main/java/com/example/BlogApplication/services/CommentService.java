package com.example.BlogApplication.services;

import com.example.BlogApplication.dtos.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId);
    void deleteComment(Integer commentId);
}
