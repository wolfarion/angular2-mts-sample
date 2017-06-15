package com.cbgr.dao.user;

import com.cbgr.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * ДАО для работы с пользователями.
 */
public interface UserDao {

    /**
     * Найти всех пользователей.
     *
     * @return список всех пользователей.
     */
    List<User> findAll();

    /**
     * Найти пользователя по ID.
     *
     * @param id идентификатор пользователя.
     */
    Optional<User> findById(String id);

    /**
     * Сохранить пользователя.
     *
     * @param user модель пользователя.
     * @return id добавленного пользователя.
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
