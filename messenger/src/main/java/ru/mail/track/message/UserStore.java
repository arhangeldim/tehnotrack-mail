package ru.mail.track.message;

/**
 * Хранилище информации о пользователе
 */
public interface UserStore {

    /**
     * Добавить пользователя в хранилище
     * Вернуть его же
     */
    User addUser(User user);

    /**
     *
     * Получить пользователя по логину/паролю
     */
    User getUser(String login, String pass);

    /**
     *
     * Получить пользователя по id, например запрос информации/профиля
     */
    User getUserById(Long id);
}
