package com.example.BlogApplication.services;

import com.example.BlogApplication.dtos.PostDTO;
import com.example.BlogApplication.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    PostDTO getPostById(Integer postId);
    List<PostDTO> getPostsByCategory(Integer categoryId);
    List<PostDTO> getPostsByUser(Integer userId);
    List<PostDTO> searchPosts(String keyword);
}
