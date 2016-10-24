package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.User;

import java.util.List;

public interface UsersProvider {
    public List<User> readAllUsers();
    public User addUser(User user);
    public User readUser(String name);

}