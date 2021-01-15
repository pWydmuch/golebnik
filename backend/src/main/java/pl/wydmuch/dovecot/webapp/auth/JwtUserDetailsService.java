package pl.wydmuch.dovecot.webapp.auth;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.webapp.auth.user.User;
import pl.wydmuch.dovecot.webapp.auth.user.UserRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public JwtUserDetailsService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User userDB = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with that login not found"));
        return new org.springframework.security.core.userdetails.User(userDB.getLogin(), userDB.getPassword(),
                new ArrayList<>());
    }


    public void authenticate(String login, String password)  {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (DisabledException e) {
            throw new RuntimeException("User not active");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Bad credentials");
        }
    }
}

