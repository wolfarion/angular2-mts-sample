package com.cbgr.business.user;

import com.cbgr.dao.user.UserDao;
import com.cbgr.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация сервиса работы с пользователями.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public String save(User user) {
        if (isUserExistsWithSameLogin(user.getLogin())) {
            throw new ValidationException("Пользователь с указанным логином уже существует в системе");
        }
        return userDao.save(user);
    }

    @Override
    public void update(User user) {
        User userOld = null;
        Optional<User> optional = userDao.findById(user.getId());
        if (optional.isPresent()) {
            userOld = optional.get();
        }
        if (userOld != null && !(userOld.getLogin().equals(user.getLogin())) && isUserExistsWithSameLogin(user.getLogin())) {
            throw new ValidationException("Пользователь с указанным логином уже существует в системе");
        }
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    /**
     * @param login логин.
     * @return {@code true}, если в системе существует пользователь с указанным логином
     */
    private boolean isUserExistsWithSameLogin(String login) {
        return findAll().stream().anyMatch(user -> Objects.equals(login, user.getLogin()));
    }
}
