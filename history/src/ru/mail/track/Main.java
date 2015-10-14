package ru.mail.track;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.authorization.FileUserStore;
import ru.mail.track.authorization.UserStore;
import ru.mail.track.comands.Command;
import ru.mail.track.comands.HelpCommand;
import ru.mail.track.comands.LoginCommand;
import ru.mail.track.session.Session;


// Это псевдокод. Показывает работу паттерна Команда
public class Main {

    private static final String EXIT = "q|exit";

    public static void main(String[] args) {

        Map<String, Command> commands = new HashMap<>();


        // В этом объекте хранится инфа о сесии
        // то есть текущее стостояние чата
        Session session = new Session();

        // Реализация интерфейса задается в одном месте
        UserStore userStore = new FileUserStore();
        AuthorizationService authService = new AuthorizationService(userStore);


        //Создаем команды
        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);

        commands.put("\\login", loginCommand);
        commands.put("\\help", helpCommand);

        InputHandler handler = new InputHandler(session, commands);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.next();
            if (line != null && line.matches(EXIT)) {
                break;
            }
            handler.handle(line);
        }



    }
}
