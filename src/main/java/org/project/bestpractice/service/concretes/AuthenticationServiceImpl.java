package org.project.bestpractice.service.concretes;

import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import org.project.bestpractice.configuration.mapper.UserMapper;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.RefreshToken;
import org.project.bestpractice.entities.Role;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.exceptions.BusinessException;
import org.project.bestpractice.handling.ErrorCode;
import org.project.bestpractice.jwt.JwtService;
import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RefreshTokenRequest;
import org.project.bestpractice.payload.RegisterRequest;
import org.project.bestpractice.repository.RefreshTokenRepository;
import org.project.bestpractice.repository.RoleRepository;
import org.project.bestpractice.repository.UserRepository;
import org.project.bestpractice.security.UserPrincipal;
import org.project.bestpractice.service.abstracts.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;
    private final RoleRepository  roleRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Role userRole = roleRepository.findByName(("ROLE_USER"))
                .orElseThrow(()-> new BusinessException(ErrorCode.ROLE_NOT_FOUND));


        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .active(true)
                .locked(false)
                .user_roles(Set.of(userRole))
                .build();
        var userDB = userRepository.save(user);
        return userMapper.toResponseFromEntity(userDB);
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmailWithRoles(request.email())
                .orElseThrow(() -> new UsernameNotFoundException(request.email()));

        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        refreshTokenRepository.save(refreshToken);
        return new AuthenticationResponse(token,refreshToken.getRefreshToken());
    }

    public void revokeAllUserRefreshTokens(User user) {
        var validUserTokens = refreshTokenRepository.findAllValidRefreshTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });

        // Hepsini tek seferde (Batch) kaydet
        refreshTokenRepository.saveAll(validUserTokens);
    }
}
