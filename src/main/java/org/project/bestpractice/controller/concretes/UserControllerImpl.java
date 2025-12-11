package org.project.bestpractice.controller.concretes;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.CustomPageResponse;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.utils.RestResponse;
import org.project.bestpractice.service.abstracts.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "rest/api/user")
public class UserControllerImpl extends RestBaseController {

    UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }



    @GetMapping(path = "/getById/{id}")
    @Operation(summary = "Id ile kullanıcı arama",
            description = "Sistem üzerinden id'si belirtilen kullanıcıyı çeker")
    public ResponseEntity<RestResponse<UserResponse>> getUserById(@PathVariable(required = true) UUID id) {
        return ok(userService.getUserById(id));
    }


    @GetMapping(path = "/getUserList")
    public ResponseEntity<RestResponse<CustomPageResponse<UserResponse>>> getUserList(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        CustomPageResponse<UserResponse> userPage = userService.getUserList(pageNumber, pageSize);
        return ok(userPage);
    }


    @PostMapping(path = "/addUser")
    @Operation(summary = "Yeni Kullanıcı Oluştur",
            description = "Sisteme yeni bir kullanıcı kaydeder. Email unique olmalıdır.")
    public ResponseEntity<RestResponse<UserResponse>> addUser(@RequestBody(required = true) @Valid UserRequest userRequest) {
        return ok(userService.addUser(userRequest));
    }


    @PutMapping(path = "/updateUser/{id}")
    public ResponseEntity<RestResponse<UserResponse>> updateUser(@PathVariable(name = "id",required = true) UUID id, @RequestBody(required = true) @Valid UserRequest userRequest) {
        return ok(userService.updateUser(userRequest,id));
    }


    @DeleteMapping(path = "/deleteUserById/{id}")
    public ResponseEntity<RestResponse<UserResponse>> deleteUserById(@PathVariable(required = true) UUID id) {
        return ok(userService.deleteUserById(id));
    }
}
