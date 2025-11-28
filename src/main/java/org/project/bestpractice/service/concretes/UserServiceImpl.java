package org.project.bestpractice.service.concretes;

import org.project.bestpractice.configuration.mapper.BaseMapper;
import org.project.bestpractice.configuration.mapper.UserMapper;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.repository.UserRepository;
import org.project.bestpractice.service.abstracts.IUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements IUserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper =  userMapper;
    }

    public UserResponse getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> userMapper.toResponseFromEntity(value)).orElse(null);

    }

    public List<UserResponse> getUserList() {
        return List.of();
    }

    public UserResponse addUser(UserRequest userRequest) {
        return null;
    }

    public UserResponse updateUser(UserRequest userRequest) {
        return null;
    }

    public UserResponse deleteUserById(UUID id) {
        return null;
    }
}
