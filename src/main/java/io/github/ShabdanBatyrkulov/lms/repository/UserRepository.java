package io.github.ShabdanBatyrkulov.lms.repository;

import io.github.ShabdanBatyrkulov.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    /*
    You can define custom query methods if needed, e.g.:
    Spring Data JPA:
    1) Looks at the method name (findByUsername)
    2) Parses it → "find a User entity where the username column matches the given argument"
    3) Generates the SQL at runtime (e.g., SELECT * FROM users WHERE username = ?)
    4) Executes it using Hibernate (the JPA provider).
    
    If your query is too complex for method names.

    Then you use @Query:
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUser(@Param("username") String username);
    */
    User findByUsername(String username);
}
