package org.project.bestpractice.service.abstracts;

import org.project.bestpractice.entities.RefreshToken;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RefreshTokenRequest;

public interface RefreshTokenService {

    public RefreshToken saveRefreshToken(RefreshToken refreshToken);

    public AuthenticationResponse refreshToken(RefreshTokenRequest request);

}

