package org.project.bestpractice.service.concretes;

import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import org.project.bestpractice.entities.RefreshToken;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.exceptions.BusinessException;
import org.project.bestpractice.handling.ErrorCode;
import org.project.bestpractice.jwt.JwtService;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RefreshTokenRequest;
import org.project.bestpractice.repository.RefreshTokenRepository;
import org.project.bestpractice.security.UserPrincipal;
import org.project.bestpractice.service.abstracts.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new SignatureException(ErrorCode.AUTH_INVALID_SIGNATURE.getMessage()));
        if(isRefreshTokenExpired(refreshToken.getExpiryDate())){
            refreshTokenRepository.delete(refreshToken);
            throw new BusinessException(ErrorCode.AUTH_TOKEN_EXPIRED);
        }
        if (refreshToken.isRevoked()) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_SIGNATURE);
        }
        String token = jwtService.generateToken(refreshToken.getUser());
        RefreshToken refreshTokenDB = saveRefreshToken(jwtService.generateRefreshToken(refreshToken.getUser()));
        refreshTokenRepository.delete(refreshToken);
        return new AuthenticationResponse(token,refreshTokenDB.getRefreshToken());
    }

    private boolean isRefreshTokenExpired(Date expiryDate) {
        return new Date().after(expiryDate);
    }

}
