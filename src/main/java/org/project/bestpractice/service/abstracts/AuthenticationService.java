package org.project.bestpractice.service.abstracts;

import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RegisterRequest;

public interface AuthenticationService {

    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public AuthenticationResponse refreshToken(String authHeader);



}
