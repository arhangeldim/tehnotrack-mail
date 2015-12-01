package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private UserStore userStore;
    private SessionManager sessionManager;

    public LoginCommand(UserStore userStore, SessionManager sessionManager) {
        this.userStore = userStore;
        this.sessionManager = sessionManager;
    }

    private void sendAcknowledge(Session session, String message) {
        try {
            SendMessage sendMessage = new SendMessage(-1L, message);
            sendMessage.setType(CommandType.MSG_SEND);
            session.getConnectionHandler().send(sendMessage);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override
    public void execute(Session session, Message msg) {

        if (session.getSessionUser() != null) {
            log.info("User {} already logged in.", session.getSessionUser());
            return;
        } else {
            LoginMessage loginMsg = (LoginMessage) msg;
            User user = userStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
            if (user == null) {
                sendAcknowledge(session, "Login Failed");
                return;
            }
            session.setSessionUser(user);
            sessionManager.registerUser(user.getId(), session.getId());
            log.info("Success login: {}", user);
            sendAcknowledge(session, "Login Ok");
        }
        /*
        А эта часть у нас уже реализована
        1 проверим, есть ли у нас уже юзер сессии
        2 посмотрим на аргументы команды
        3 пойдем в authorizationService и попробуем получить юзера
         */
    }
}
