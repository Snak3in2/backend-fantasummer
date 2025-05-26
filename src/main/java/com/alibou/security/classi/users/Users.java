package com.alibou.security.classi.users;

import com.alibou.security.classi.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;
  private String email;
  private String password_hash;

  @Enumerated(EnumType.STRING)
  private AuthProvider auth_provider;
  private String provider_id;
  private String avatar_url;
  private Integer total_points;
  private Integer completed_challenges;
  private Timestamp created_at;
  private Timestamp updated_at;

  @Enumerated(EnumType.STRING)
  private Role tipo;

  @JsonIgnore
  @OneToMany(mappedBy = "users")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority>authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(tipo.name()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return password_hash;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
