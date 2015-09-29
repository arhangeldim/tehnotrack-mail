package ru.mail.track;


import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Scanner;

public class IOBasic {


    // Using scanner
    public static void readConsoleScanner() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("type number:");
        int num = scanner.nextInt();
        System.out.println(">" + num);

        System.out.println("type string");
        String str = scanner.next();
        System.out.println(">" + str);
    }

    // Using BufferedReader
    public static void readConsoleBuffered() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("Please type here");
        while ((line = input.readLine()) != null) {
            if (line.equals("q")) {
                System.out.println("Exit...");
                break;
            }
            System.out.println(">" + line);
        }
    }

    // Using Console
    public static void readConsoleConsole() throws Exception {
        Console console = System.console();
        if (console == null) {
            System.err.println("Unable to obtain console");
            return;
        }

        String password = new String (console.readPassword ("Enter password: "));
        System.out.println(password);

    }

    public static void readFile(String name) {

    }

    public static void main(String[] args) throws Exception {
        //readConsoleScanner();
        //readConsoleBuffered();
        //readConsoleConsole();



        String data[] = {"A", "B", "C", null, "D"};
        for (String arg : data) {
            System.out.println(arg.length());
        }


    }
}
