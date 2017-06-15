package com.cbgr.dao.user;

import com.cbgr.entity.user.User;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ДАО для работы с пользователями.
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final Map<String, User> userRepository = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        userRepository.put("123", new User("123", "Пупкин Василий", "vpupkin", "123", LocalDate.of(1990, Month.JANUARY, 1), User.Sex.MALE));
    }

    @Override
    public List<User> findAll() {
        return ImmutableList.copyOf(userRepository.values());
    }

    @Override
    public Optional<User> findById(String id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(userRepository.get(id));
    }

    @Override
    public String save(User user) {
        Objects.requireNonNull(user);

        user.setId(UUID.randomUUID().toString());
        userRepository.put(user.getId(),user);
        return user.getId();
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user);

        Optional<User> optional = findById(user.getId());
        if (optional.isPresent()) {
            optional.get().update(user);
        }
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);

        userRepository.remove(id);
    }
}
