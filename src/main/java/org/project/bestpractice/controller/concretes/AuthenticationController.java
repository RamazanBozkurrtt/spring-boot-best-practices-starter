package org.project.bestpractice.controller.concretes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RefreshTokenRequest;
import org.project.bestpractice.payload.RegisterRequest;
import org.project.bestpractice.service.abstracts.AuthenticationService;
import org.project.bestpractice.service.abstracts.RefreshTokenService;
import org.project.bestpractice.utils.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Security and Login Operations")
public class AuthenticationController extends RestBaseController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @Operation(summary = "User Register", description = "Creates a new user account.")
    public ResponseEntity<RestResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return created(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Login", description = "Returns Access and Refresh tokens.")
    public ResponseEntity<RestResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Token", description = "Generates a new Access Token using a Refresh Token.")
    public ResponseEntity<RestResponse<AuthenticationResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        // @RequestBody ekledim, JSON olarak gelmeli.
        return ok(refreshTokenService.refreshToken(request));
    }
}