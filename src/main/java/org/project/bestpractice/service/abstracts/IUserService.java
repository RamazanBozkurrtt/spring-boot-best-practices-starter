package org.project.bestpractice.service.abstracts;

import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserResponse getUserById(UUID id);

    List<UserResponse> getUserList();

    UserResponse addUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    UserResponse deleteUserById(UUID id);

}
