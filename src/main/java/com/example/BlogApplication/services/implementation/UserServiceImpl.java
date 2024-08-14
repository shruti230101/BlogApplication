package com.example.BlogApplication.services.implementation;

import com.example.BlogApplication.config.MessageConfig;
import com.example.BlogApplication.dtos.UserDTO;
import com.example.BlogApplication.entities.User;
import com.example.BlogApplication.exceptions.ResourceNotFoundException;
import com.example.BlogApplication.repositories.UserRepository;
import com.example.BlogApplication.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageConfig messageConfig;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = this.userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        User user = this.userRepository
                        .findById(userId.toString())
                        .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                                this.messageConfig.RESOURCE_USER, this.messageConfig.RESOURCE_FIELD, userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepository
                        .findById(userId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_USER, this.messageConfig.RESOURCE_FIELD, userId));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(this.messageConfig.RESOURCE_NOT_FOUND_MESSAGE_FORMAT,
                        this.messageConfig.RESOURCE_USER, this.messageConfig.RESOURCE_FIELD, userId));
        this.userRepository.delete(user);
    }
}
