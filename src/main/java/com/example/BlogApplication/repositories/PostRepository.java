package com.example.BlogApplication.repositories;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.entities.Category;
import com.example.BlogApplication.entities.Posts;
import com.example.BlogApplication.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    List<Posts> findByUser(User user);
    List<Posts> findByCategory(Category category);
    @Query(value = "SELECT * FROM posts WHERE post_title LIKE CONCAT('%', :title, '%')", nativeQuery = true)
    List<Posts> searchByTitle(@Param("title") String title);
}
