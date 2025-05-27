package com.alibou.security.classi.users;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

  Optional<Users> findByEmail(String email); // o findByUsername

  Optional<Users> findByUsername(String username);
}