package com.alibou.security.classi.users;

import com.alibou.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            service.createUser(request);
            response.put("message", "Utente creato con successo.");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUsers(@RequestBody List<Integer> userIds) {
        service.eliminaUtenti(userIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Users>> getUtenti() {
        List<Users> utenti = service.getAllUtenti();
        return ResponseEntity.ok(utenti);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Integer id,
            @RequestBody Users updatedUsers
    ) {
        Users users = service.updateUser(id, updatedUsers);
        return ResponseEntity.ok(users);
    }
}
