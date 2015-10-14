package ru.mail.track;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ru.mail.track.comands.Command;
import ru.mail.track.session.Session;

/**
 * Основная задача класса принимать на вход данные от пользователя и решать, что с ними делать
 *
 * Можно крутить в цикле
 *
 */
public class InputHandler {

    private Session session;

    private Map<String, Command> commandMap;

    public InputHandler(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }

    public void handle(String data) {
        // проверяем на спецсимвол команды
        // Это пример!
        if (data.startsWith("\\")) {
            String[] tokens = data.split(" ");

            // Получим конкретную команду, но нам не важно что за команда,
            // у нее есть метод execute()
            Command cmd = commandMap.get(tokens[0]);
            cmd.execute(session, tokens);
        } else {
            System.out.println(">" + data);
        }
    }

}



