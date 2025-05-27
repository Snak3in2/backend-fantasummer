package com.alibou.security.dto;

import com.alibou.security.classi.users.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private AuthProvider authProvider;
    private String avatar_url;
    private Integer total_points;
}
