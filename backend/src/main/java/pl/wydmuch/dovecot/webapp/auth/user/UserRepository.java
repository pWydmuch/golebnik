package pl.wydmuch.dovecot.webapp.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByLogin(String login);

}
