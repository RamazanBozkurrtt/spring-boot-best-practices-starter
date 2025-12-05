package org.project.bestpractice.controller.concretes;

import jakarta.validation.Valid;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.RootEntity;
import org.project.bestpractice.service.abstracts.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "rest/api/user")
public  class UserControllerImpl extends RestBaseController {

    IUserService userService;

    public UserControllerImpl(IUserService userService) {
        this.userService = userService;
    }



    @GetMapping(path = "/getById/{id}")
    public RootEntity<UserResponse> getUserById(@PathVariable(required = true) UUID id) {
        return ok(userService.getUserById(id));
    }


    @GetMapping(path = "/getUserList")
    public RootEntity<List<UserResponse>> getUserList() {
        return ok(userService.getUserList());
    }


    @PostMapping(path = "/addUser")
    public RootEntity<UserResponse> addUser(@RequestBody(required = true) @Valid UserRequest userRequest) {
        return ok(userService.addUser(userRequest));
    }


    @PutMapping(path = "/updateUser/{id}")
    public RootEntity<UserResponse> updateUser(@PathVariable(name = "id",required = true) UUID id,@RequestBody(required = true) @Valid UserRequest userRequest) {
        return ok(userService.updateUser(userRequest,id));
    }


    @DeleteMapping(path = "/deleteUserById/{id}")
    public RootEntity<UserResponse> deleteUserById(@PathVariable(required = true) UUID id) {
        return ok(userService.deleteUserById(id));
    }
}
