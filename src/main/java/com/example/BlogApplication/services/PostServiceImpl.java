package com.example.BlogApplication.services;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.dtos.PostDTO;
import com.example.BlogApplication.entities.Category;
import com.example.BlogApplication.entities.Posts;
import com.example.BlogApplication.entities.User;
import com.example.BlogApplication.exceptions.ResourceNotFoundException;
import com.example.BlogApplication.payloads.PostResponse;
import com.example.BlogApplication.repositories.CategoryRepository;
import com.example.BlogApplication.repositories.PostRepository;
import com.example.BlogApplication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageConfig messageConfig;
    @Autowired
    private PostResponse postResponse;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_USER, this.messageConfig.RESOURCE_FIELD, userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_CATEGORY, this.messageConfig.RESOURCE_FIELD, categoryId));
        Posts post = this.modelMapper.map(postDTO, Posts.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Posts newPost = this.postRepository.save(post);
        return this.modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Posts post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_POST, this.messageConfig.RESOURCE_FIELD, postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        Posts updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Posts post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_POST, this.messageConfig.RESOURCE_FIELD, postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Posts> pagePosts = this.postRepository.findAll(pageable);
        List<Posts> posts = pagePosts.getContent();
        List<PostDTO> postDTOS = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).toList();
        this.postResponse.setContent(postDTOS);
        this.postResponse.setPageNumber(pagePosts.getNumber());
        this.postResponse.setPageSize(pagePosts.getSize());
        this.postResponse.setTotalPages(pagePosts.getTotalPages());
        this.postResponse.setTotalElements(pagePosts.getTotalElements());
        this.postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Posts post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_POST, this.messageConfig.RESOURCE_FIELD, postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_CATEGORY, this.messageConfig.RESOURCE_FIELD, categoryId));
        List<Posts> posts = this.postRepository.findByCategory(category);
        return posts.stream()
                    .map(post -> this.modelMapper.map(post, PostDTO.class))
                    .toList();
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        User user = this.userRepository.findById(userId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_USER, this.messageConfig.RESOURCE_FIELD, userId));
        List<Posts> posts = this.postRepository.findByUser(user);
        return posts.stream()
                .map(post -> this.modelMapper.map(post, PostDTO.class))
                .toList();
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Posts> posts = this.postRepository.searchByTitle(keyword);
        return posts.stream()
                    .map(post -> this.modelMapper.map(post, PostDTO.class))
                    .toList();
    }
}
