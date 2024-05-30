package com.example.BlogApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties")
public class MessageConfig {
    @Value("${response.category.deleted}")
    public String CATEGORY_DELETED;
    @Value("${response.post.deleted}")
    public String POST_DELETED;
    @Value("${response.user.deleted}")
    public String USER_DELETED;
    @Value("${response.comment.deleted}")
    public String COMMENT_DELETED;
    @Value("${response.globalException.message}")
    public String GLOBAL_EXCEPTION_MESSAGE;
    @Value("${resourceNotFoundException.message.format}")
    public String RESOURCE_NOT_FOUND_MESSAGE_FORMAT;
    @Value("${response.resource.category}")
    public String RESOURCE_CATEGORY;
    @Value("${response.resource.user}")
    public String RESOURCE_USER;
    @Value("${response.resource.post}")
    public String RESOURCE_POST;
    @Value("${response.resource.comment}")
    public String RESOURCE_COOMMENT;
    @Value("${response.resource.field}")
    public String RESOURCE_FIELD;
}