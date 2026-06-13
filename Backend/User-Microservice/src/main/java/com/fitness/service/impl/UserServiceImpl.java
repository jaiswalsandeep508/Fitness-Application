package com.fitness.service.impl;

import com.fitness.dto.request.UserRequest;
import com.fitness.dto.response.UserResponse;
import com.fitness.exception.ResourceAlreadyExistException;
import com.fitness.exception.ResourceNotFoundException;
import com.fitness.modal.User;
import com.fitness.repository.UserRepository;
import com.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserResponse register(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistException(
                    "User already exist with email : " + request.getEmail());
        }

        User user = modelMapper.map(request, User.class);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException
                        ("User not found with userId : "+userId));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public boolean validateUser(String userId) {
        return userRepository.existsById(userId);
    }


}
