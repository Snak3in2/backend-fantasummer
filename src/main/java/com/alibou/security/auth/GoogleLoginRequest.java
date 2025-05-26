package com.alibou.security.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleLoginRequest {
    private String email;
    private String name;
    private String imageUrl;
    private String googleId;
}
