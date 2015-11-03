package ru.mail.track.message;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class UserStoreStub implements UserStore {

    private static Map<Long, User> users = new HashMap<>();

    static {
        User u0 = new User("A", "1");
        u0.setId(0L);

        User u1 = new User("B", "1");
        u1.setId(1L);

        User u2 = new User("C", "1");
        u2.setId(2L);

        User u3 = new User("D", "1");
        u3.setId(3L);

        users.put(0L, u0);
        users.put(1L, u1);
        users.put(2L, u2);
        users.put(3L, u3);
    }

    @Override
    public User addUser(User user) {
        User item = users.put(user.getId(), user);
        return item;
    }

    @Override
    public User getUser(String login, String pass) {
        for (User user : users.values()) {
            if (user.getName().equals(login) && user.getPass().equals(pass)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }
}
