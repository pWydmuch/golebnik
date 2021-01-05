package pl.wydmuch.dovecot.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.webapp.auth.dtos.JwtRequest;
import pl.wydmuch.dovecot.webapp.auth.dtos.JwtResponse;
import pl.wydmuch.dovecot.webapp.auth.dtos.PasswordChangeDto;
import pl.wydmuch.dovecot.webapp.auth.security.JwtTokenUtil;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin("*")
public class UserController {

    private UserService userService;
    private JwtUserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;


    public UserController(UserService userService, JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public User getUser(String login) {
        return userService.findByLogin(login);
    }

    @PutMapping("/my-data")
    public ResponseEntity<Void> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User user) {
        String login = jwtTokenUtil.getLoginFromHeader(authorizationHeader);
        userService.update(user, login);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/my-password")
    public void updatePassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PasswordChangeDto passwordChangeDto)  {
        String login = jwtTokenUtil.getLoginFromHeader(authorizationHeader);
        authenticate(login, passwordChangeDto.getOldPassword());
        userService.changePassword(login, passwordChangeDto);
    }

    @GetMapping("/my-data")
    public User getUserData(@RequestHeader("Authorization") String authorizationHeader) {
        String login = jwtTokenUtil.getLoginFromHeader(authorizationHeader);
        return userService.findByLogin(login);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        userService.registerNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest){
        String login = authenticationRequest.getLogin();
        authenticate(login, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token, login), HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseBody
    public String handleErrors(Throwable error){
        System.out.println(error.getMessage());
        return error.getMessage();
    }

    private void authenticate(String login, String password) {
        userDetailsService.authenticate(login, password);
    }

}
