package pl.wydmuch.dovecot.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.webapp.auth.dtos.PasswordChangeDto;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with this id cannot be found"));
    }

    public User registerNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("User with this email already exists");
        if (userRepository.existsByLogin(user.getLogin()))
            throw new RuntimeException("User with this login already exists");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void changePassword(String login, PasswordChangeDto passwordChangeDto) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User with this login doesn't exists"));
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);
    }

    public User update(User user, String login) {
        User usr = findByLogin(login);
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());
        usr.setEmail(user.getEmail());
        usr.setGender(user.getGender());
        usr.setBirthday(user.getBirthday());
        userRepository.save(usr);
        return user;
    }
}
