package com.example.BlogApplication.controllers;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.dtos.CommentDTO;
import com.example.BlogApplication.payloads.APIResponse;
import com.example.BlogApplication.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private MessageConfig messageConfig;

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId)
    {
        CommentDTO comment = this.commentService.createComment(commentDTO, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new APIResponse(this.messageConfig.COMMENT_DELETED, true, HttpStatus.OK), HttpStatus.OK);
    }
}
