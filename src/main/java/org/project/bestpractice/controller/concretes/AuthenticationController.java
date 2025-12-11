package org.project.bestpractice.controller.concretes;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.bestpractice.controller.RestBaseController;
import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RegisterRequest;
import org.project.bestpractice.service.abstracts.AuthenticationService;
import org.project.bestpractice.utils.RestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Security Transactions")
public class AuthenticationController extends RestBaseController {

    private final AuthenticationService authenticationService;




    @PostMapping(path = "/register")
    @Operation(summary = "User register",description = "This method creates a new user and return token")
    public ResponseEntity<RestResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest registerRequest) {
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
            HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        AuthenticationResponse response = authenticationService.refreshToken(authHeader);
        return ok(response);
    }
}
