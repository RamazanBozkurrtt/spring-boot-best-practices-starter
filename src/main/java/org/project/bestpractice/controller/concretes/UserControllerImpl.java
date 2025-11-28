package org.project.bestpractice.controller.concretes;

import jakarta.validation.Valid;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.controller.abstracts.IUserController;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.RootEntity;
import org.project.bestpractice.service.abstracts.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "rest/api/user")
public  class UserControllerImpl <T> extends RestBaseController<T> implements IUserController {

    IUserService userService;

    public UserControllerImpl(IUserService userService) {
        this.userService = userService;
    }


    @Override
    @GetMapping(path = "/getById/{id}")
    public RootEntity<UserResponse> getUserById(@PathVariable(required = true) UUID id) {
        return null;
    }

    @Override
    @GetMapping(path = "/getUserList")
    public RootEntity<List<UserResponse>> getUserList() {
        return null;
    }

    @Override
    @PutMapping(path = "/addUser")
    public RootEntity<UserResponse> addUser(@RequestBody(required = true) @Valid UserRequest userRequest) {
        return null;
    }

    @Override
    @PutMapping(path = "/updateUser")
    public RootEntity<UserResponse> updateUser(@RequestBody(required = true) @Valid UserRequest userRequest) {
        return null;
    }

    @Override
    public RootEntity<UserResponse> deleteUserById(UUID id) {
        return null;
    }
}
