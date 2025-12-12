package org.project.bestpractice.service.concretes;

import jakarta.transaction.Transactional;
import org.project.bestpractice.configuration.mapper.UserMapper;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.CustomPageResponse;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.exceptions.BusinessException;
import org.project.bestpractice.handling.ErrorCode;
import org.project.bestpractice.repository.UserRepository;
import org.project.bestpractice.service.abstracts.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    AuthenticationServiceImpl authenticationServiceImpl;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,  AuthenticationServiceImpl authenticationServiceImpl) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationServiceImpl = authenticationServiceImpl;

    }

    public UserResponse getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> userMapper.toResponseFromEntity(value)).orElse(null);
    }

    public CustomPageResponse<UserResponse> getUserList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate"));
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> dtoList = userMapper.toResponseListFromEntityList(userPage.getContent());



        return CustomPageResponse.of(userPage,dtoList);
    }

    @Transactional
    public UserResponse addUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = userMapper.toEntityFromRequest(userRequest);
        userRepository.save(user);
        return userMapper.toResponseFromEntity(user);
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest,UUID id) {
        Optional<User> userDb = userRepository.findById(id);
        if (userDb.isPresent()) {
            userDb.get().setFirstname(userRequest.getFirstname());
            userDb.get().setLastname(userRequest.getLastname());
            userDb.get().setEmail(userRequest.getEmail());
            userDb.get().setPassword(userRequest.getPassword());
            userRepository.save(userDb.get());
            return userMapper.toResponseFromEntity(userDb.get());
        }
        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    @Transactional
    public UserResponse deleteUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return userMapper.toResponseFromEntity(user.get());
        }
        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public UserResponse deleteUserSoftlyById(UUID id) {
        var user = userRepository.findById(id).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.setActive(false);
        authenticationServiceImpl.revokeAllUserTokens(user);
        userRepository.save(user);
        return userMapper.toResponseFromEntity(user);
    }
}
