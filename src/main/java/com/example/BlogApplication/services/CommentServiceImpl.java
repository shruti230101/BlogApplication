package com.example.BlogApplication.services;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.dtos.CommentDTO;
import com.example.BlogApplication.entities.Comment;
import com.example.BlogApplication.entities.Posts;
import com.example.BlogApplication.exceptions.ResourceNotFoundException;
import com.example.BlogApplication.repositories.CommentRepository;
import com.example.BlogApplication.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MessageConfig messageConfig;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Posts post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                            this.messageConfig.RESOURCE_POST, this.messageConfig.RESOURCE_FIELD, postId));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_COMMENT, this.messageConfig.RESOURCE_FIELD, commentId));
        this.commentRepository.delete(comment);
    }
}
