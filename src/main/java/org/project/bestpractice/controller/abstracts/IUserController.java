package org.project.bestpractice.controller.abstracts;

import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IUserController {

    RootEntity<UserResponse> getUserById(UUID id);

    RootEntity<List<UserResponse>> getUserList();

    RootEntity<UserResponse>addUser(UserRequest userRequest);

    RootEntity<UserResponse>updateUser(UserRequest userRequest);

    RootEntity<UserResponse> deleteUserById(UUID id);

}
