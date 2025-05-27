package com.alibou.security.classi.users;

import com.alibou.security.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        // userDetails.getUsername() di solito è l'email
        Users user = userService.getByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getAuth_provider(),
                user.getAvatar_url(),
                user.getTotal_points()
        );
        return ResponseEntity.ok(userDTO);
    }
}