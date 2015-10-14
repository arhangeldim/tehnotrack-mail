package ru.mail.track.authorization;

import ru.mail.track.session.User;

/**
 *
 */
public class FileUserStore implements UserStore {

    @Override
    public boolean isUserExist(String name) {
        return false;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUser(String name, String pass) {
        return null;
    }
}
