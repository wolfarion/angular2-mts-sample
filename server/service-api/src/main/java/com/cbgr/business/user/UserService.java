package com.cbgr.business.user;

import com.cbgr.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с пользователями.
 */
public interface UserService {

    /**
     * Найти всех пользователей.
     */
    List<User> findAll();

    /**
     * Найти пользователя по id.
     *
     * @param id идентификатор пользователя.
     */
    Optional<User> findById(String id);

    /**
     * Сохранить пользователя.
     *
     * @param user модель пользователя.
     */
    String save(User user);

    /**
     * Обновить пользователя.
     *
     * @param user модель пользователя.
     */
    void update(User user);


    /**
     * Удалить пользователя.
     *
     * @param id идентификатор пользователя.
     */
    void delete(String id);
}
