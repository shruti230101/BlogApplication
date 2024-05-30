package com.example.BlogApplication.exceptions;

import com.example.BlogApplication.config.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceNotFoundException extends RuntimeException{

    @Autowired
    private MessageConfig messageConfig;
    final String resourceName;
    final String fieldName;
    final long fieldValue;

    public ResourceNotFoundException(String messageFormat, String resourceName, String fieldName, long fieldValue)
    {
        super(String.format(messageFormat, resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
