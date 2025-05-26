package com.alibou.security.classi.token;

import com.alibou.security.classi.users.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query("""
      SELECT t FROM Token t 
      WHERE t.users.id = :id AND (t.expired = false OR t.revoked = false)
      """)
  List<Token> findAllValidTokenByUser(@Param("id") Integer id);

  Optional<Token> findByToken(String token);

  @Modifying
  @Transactional
  @Query("DELETE FROM Token t WHERE t.users.id = :userId")
  void deleteByUserId(@Param("userId") Integer userId);
}
