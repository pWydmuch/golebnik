package pl.wydmuch.dovecot.webapp.auth.dtos;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String login;

    public JwtResponse(String jwttoken, String login) {
        this.jwttoken = jwttoken;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
