package org.project.bestpractice.service.abstracts;

import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.CustomPageResponse;
import org.project.bestpractice.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse getUserById(UUID id);

    CustomPageResponse<UserResponse> getUserList(int pageNumber, int pageSize);

    UserResponse addUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest,UUID id);

    UserResponse deleteUserById(UUID id);

}
