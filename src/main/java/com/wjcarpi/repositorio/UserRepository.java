package com.wjcarpi.repositorio;

import com.wjcarpi.modelo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByGmail(String gmail);
    List<User> findByRoleName(String roleName);

    boolean existsByUsername(String username);
    boolean existsByGmail(String gmail);
}
