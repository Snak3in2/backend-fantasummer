package com.alibou.security.auth;

import com.alibou.security.classi.users.AuthProvider;
import com.alibou.security.classi.users.Role;
import com.alibou.security.config.JwtService;
import com.alibou.security.classi.token.Token;
import com.alibou.security.classi.token.TokenRepository;
import com.alibou.security.classi.token.TokenType;
import com.alibou.security.classi.users.Users;
import com.alibou.security.classi.users.UserRepository;
import com.alibou.security.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        if (!request.getPassword().equals(EncryptionUtils.decrypt(user.getPassword()))) {
            throw new RuntimeException("Password errata!");
        }


        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(Users users, String jwtToken) {
        var token = Token.builder()
                .users(users)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Users users) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(users.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse loginWithGoogle(GoogleLoginRequest request) {
        var optionalUser = repository.findByUsername(request.getEmail());

        Users user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = Users.builder()
                    .username(request.getEmail())        // Puoi usare l'email come username
                    .email(request.getEmail())           // Salvi anche l'email
                    .auth_provider(AuthProvider.google)  // <-- IMPORTANTE
                    .provider_id(request.getGoogleId())  // <-- opzionale, utile per tracciamento
                    .avatar_url(request.getImageUrl())   // <-- opzionale, per immagine profilo
                    .password_hash("")                   // Nessuna password (login social)
                    .tipo(Role.USER)                     // <-- tipo account: USER o ADMIN
                    .created_at(new Timestamp(System.currentTimeMillis()))
                    .updated_at(new Timestamp(System.currentTimeMillis()))
                    .build();

            repository.save(user);
        }

        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

}