package ru.mail.track.authorization;


import ru.mail.track.session.User;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    void startAuthorization() {
        if (isLogin()) {
            login();
        }
    }

    public User login() {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, pass)


        return null;
    }

    public User creatUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)

        return null;
    }

    private boolean isLogin() {
        return false;
    }
}
