package org.project.bestpractice.service.concretes;

import org.project.bestpractice.entities.Token;
import org.project.bestpractice.entities.User;
import org.project.bestpractice.jwt.JwtService;
import org.project.bestpractice.jwt.TokenType;
import org.project.bestpractice.payload.AuthenticationRequest;
import org.project.bestpractice.payload.AuthenticationResponse;
import org.project.bestpractice.payload.RegisterRequest;
import org.project.bestpractice.repository.TokenRepository;
import org.project.bestpractice.repository.UserRepository;
import org.project.bestpractice.security.UserPrincipal;
import org.project.bestpractice.service.abstracts.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(true)
                .locked(false)
                .build();

        var savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(new UserPrincipal(savedUser));
        var refreshToken = jwtService.generateRefreshToken(new UserPrincipal(savedUser));

        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException(request.email()));

        var userPrincipal = new UserPrincipal(user);
        var jwtToken = jwtService.generateToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthenticationResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token bulunamadı!");
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new RuntimeException("Token geçersiz!");
        }
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));

        var userPrincipal = new UserPrincipal(user);
        if (!jwtService.isTokenValid(refreshToken, userPrincipal)) {
            throw new RuntimeException("Refresh token süresi dolmuş veya geçersiz!");
        }
        // Yeni Access Token üret
        var accessToken = jwtService.generateToken(userPrincipal);
        saveUserToken(user, accessToken);

        // Service artık direkt cevabı dönüyor, HTTP ile uğraşmıyor.
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
