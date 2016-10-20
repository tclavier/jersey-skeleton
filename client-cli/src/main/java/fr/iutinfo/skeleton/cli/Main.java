package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.User;

public class Main {

    public static final int MISSING_ARGS = 1;

    public static void main(String[] args) {
        if(args.length != 1) {
            System.exit(MISSING_ARGS);
        }
        RestClient restClient = new RestClient(args[0]);
        User thomas = restClient.readUser("Thomas");
        System.out.println(thomas.getName());
    }
}
