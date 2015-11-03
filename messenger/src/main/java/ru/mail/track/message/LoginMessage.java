package ru.mail.track.message;

import ru.mail.track.comands.CommandType;

/**
 *
 */
public class LoginMessage extends Message {

    private String login;
    private String pass;

    public LoginMessage() {
        setType(CommandType.USER_LOGIN);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
