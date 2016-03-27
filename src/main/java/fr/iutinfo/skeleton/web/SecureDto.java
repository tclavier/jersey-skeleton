package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.api.User;

import java.util.List;

public class SecureDto {
    private List<User> users;
    private User currentUser;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
