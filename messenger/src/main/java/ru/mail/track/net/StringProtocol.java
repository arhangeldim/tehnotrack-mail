package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.comands.CommandType;
import ru.mail.track.context.AutoComponent;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;

/**
 *
 */
@AutoComponent
public class StringProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(StringProtocol.class);

    public static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) {
        String str = new String(bytes);
        log.info("decoded: {}", str);
        String[] tokens = str.split(DELIMITER);
        CommandType type = CommandType.valueOf(tokens[0]);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setLogin(tokens[1]);
                loginMessage.setPass(tokens[2]);
                return loginMessage;
            case MSG_SEND:
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(Long.valueOf(tokens[1]));
                sendMessage.setMessage(tokens[2]);
                return sendMessage;
            default:
                throw new RuntimeException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message msg) {
        StringBuilder builder = new StringBuilder();
        CommandType type = msg.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case USER_LOGIN:
                LoginMessage loginMessage = (LoginMessage) msg;
                builder.append(loginMessage.getLogin()).append(DELIMITER);
                builder.append(loginMessage.getPass()).append(DELIMITER);
                break;
            case MSG_SEND:
                SendMessage sendMessage = (SendMessage) msg;
                builder.append(sendMessage.getChatId()).append(DELIMITER);
                builder.append(sendMessage.getMessage()).append(DELIMITER);
                break;
            default:
                throw new RuntimeException("Invalid type: " + type);


        }
        log.info("encoded: {}", builder.toString());
        return builder.toString().getBytes();
    }



}
