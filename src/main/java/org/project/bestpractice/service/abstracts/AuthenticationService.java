package org.project.bestpractice.service.abstracts;

import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RefreshTokenRequest;
import org.project.bestpractice.payload.RegisterRequest;

public interface AuthenticationService {

    public UserResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);

}
