package org.project.bestpractice.controller.concretes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.CustomPageResponse;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.service.abstracts.UserService;
import org.project.bestpractice.utils.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Operations related to users")
public class UserControllerImpl extends RestBaseController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Fetches a user by their unique identifier.")
    public ResponseEntity<RestResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        return ok(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Fetches users with pagination.")
    public ResponseEntity<RestResponse<CustomPageResponse<UserResponse>>> getUserList(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ok(userService.getUserList(pageNumber, pageSize));
    }

    @PostMapping
    @Operation(summary = "Create User", description = "Registers a new user to the system.")
    public ResponseEntity<RestResponse<UserResponse>> addUser(@RequestBody @Valid UserRequest userRequest) {
        return created(userService.addUser(userRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user.")
    public ResponseEntity<RestResponse<UserResponse>> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequest userRequest) {
        return ok(userService.updateUser(userRequest, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Soft deletes a user from the system.")
    public ResponseEntity<RestResponse<UserResponse>> deleteUserById(@PathVariable UUID id) {
        return ok(userService.deleteUserSoftlyById(id));
    }
}