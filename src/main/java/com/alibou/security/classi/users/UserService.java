package com.alibou.security.classi.users;

import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.classi.token.TokenRepository;
import com.alibou.security.utils.EncryptionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void createUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalStateException("Errore: username già in uso!");
        }

        var user = Users.builder()
                .username(request.getNome())
                .username(request.getUsername())
                .password_hash(EncryptionUtils.encrypt(request.getPassword())) // cifrata!
                .tipo(request.getTipo())
                .build();
        userRepository.save(user);
    }


    public List<Users> getAllUtenti() {
        List<Users> utenti = userRepository.findAll();
        utenti.forEach(u -> u.setPassword_hash(EncryptionUtils.decrypt(u.getPassword())));
        return utenti;
    }


    public Users updateUser(Integer id, Users updatedUsers) {
        Users users = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Utente non trovato"));

        users.setUsername(updatedUsers.getUsername());
        users.setTipo(updatedUsers.getTipo());
        users.setUsername(updatedUsers.getUsername());
        users.setPassword_hash(EncryptionUtils.encrypt(updatedUsers.getPassword()));

        return userRepository.save(users);
    }

    @Transactional
    public void eliminaUtenti(List<Integer> ids) {
        for (Integer userId : ids) {
            tokenRepository.deleteByUserId(userId);
        }
        userRepository.deleteInBatchByIdIn(ids);
    }



}