package pl.wydmuch.dovecot.webapp.auth.dtos;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
//    private String email;
    private String login;
    private String password;
    //need default constructor for JSON Parsing
    public JwtRequest()
    {
    }

    public JwtRequest(String login, String password) {
//        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtRequest{" +

                ", password='" + password + '\'' +
                '}';
    }
}