package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.User;

import java.util.Scanner;

public class Main {

    public static final int MISSING_ARGS = 1;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java -jar client-cli-1.0-SNAPSHOT.jar http://localhost:8080");
            System.exit(MISSING_ARGS);
        }

        String url = args[0] + "/v1/";
        System.out.println("Api url : " + url);
        RestClient restClient = new RestClient(url);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel est ton nom ?");
        String nom = scanner.nextLine();
        User user = new User();
        user.setName(nom);
        System.out.println("Quel est ton mail ?");
        String email = scanner.nextLine();
        user.setEmail(email);

        user = restClient.addUser(user);
        System.out.println(user.toString());
    }
}
