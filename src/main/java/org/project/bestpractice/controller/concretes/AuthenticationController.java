package org.project.bestpractice.controller.concretes;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.bestpractice.controller.RestBaseController;
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
@Tag(name = "Authentication", description = "Security Transactions")
public class AuthenticationController extends RestBaseController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(path = "/register")
    @Operation(summary = "User register",description = "This method creates a new user")
    public ResponseEntity<RestResponse<AuthenticationResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return created(authenticationService.register(registerRequest));
    }

    @PostMapping(path = "/authenticate")
    @Operation(summary = "Login", description = "Login with email and password. This method returns an Access Token and a Refresh Token")
    public ResponseEntity<RestResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping(path = "/refresh-token")
    @Operation(summary = "Refresh Token", description = "This method gets a new Access token by using Refresh Token")
    public ResponseEntity<RestResponse<AuthenticationResponse>> refreshToken(
            RefreshTokenRequest request) {
        return ok(refreshTokenService.refreshToken(request));
    }
}
