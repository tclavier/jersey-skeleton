package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static final int MISSING_ARGS = 1;
    private static UsersProvider usersProvider;
    private static Scanner scanner;

    public static void main(String[] args) {
        checkArgs(args);
        initUrlAndProvider(args[0]);
        scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            String action = menu();
            switch (action) {
                case "a":
                    addUser();
                    break;
                case "l":
                    listUSer();
                    break;
                case "f":
                    running = false;
            }
        }
    }

    private static void listUSer() {
        List<User> users = usersProvider.readAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void addUser() {
        User user = new User();
        user.setName(queryAndReadLine("Quel est ton nom ?"));
        user.setEmail(queryAndReadLine("Quel est ton mail ?"));
        usersProvider.addUser(user);
    }

    private static String menu() {
        boolean badAnswer = true;
        String answer = "";
        while (badAnswer) {
            System.out.println("\nAction ?");
            System.out.println(" a : ajouter un utilisateur");
            System.out.println(" l : lister les utilisateurs");
            System.out.println(" f : finir");
            answer = scanner.nextLine();
            if ("a".equals(answer) || "l".equals(answer) || "f".equals(answer)) badAnswer = false;
        }
        return answer;
    }

    private static String queryAndReadLine(String query) {
        System.out.println(query);
        return scanner.nextLine();
    }

    private static void initUrlAndProvider(String arg) {
        String url = arg + "/v1/";
        System.out.println("Api url : " + url);
        usersProvider = new RemoteUsersProvider(url);
    }

    private static void checkArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java -jar client-cli-1.0-SNAPSHOT.jar http://localhost:8080");
            System.exit(MISSING_ARGS);
        }
    }
}
