package com.cbgr.entity.user;

import com.cbgr.entity.Persistable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Пользователь.
 */
public class User implements Persistable<String> {

    /** Уникальный идентификатор пользователя. */
    private String id;

    /** Отображаемое имя. */
    private String displayName;

    /** Идентификатор учетной записи. */
    private String login;

    /** Пароль. */
    private String password;

    /** Дата рождения. */
    private LocalDate birthDate;

    /** Пол. */
    private Sex sex;

    public User() {
    }

    public User(String id, String displayName, String login, String password, LocalDate birthDate, Sex sex) {
        this.id = id;
        this.displayName = displayName;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public void update(User user) {
        displayName = user.displayName;
        login = user.login;
        password = user.password;
        birthDate = user.birthDate;
        sex = user.sex;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(displayName, user.displayName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(birthDate, user.birthDate) &&
                sex == user.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName, login, password, birthDate, sex);
    }

    /** Пол. */
    public enum Sex {

        /** Мужской пол. */
        MALE("Муж."),

        /** Женский пол. */
        FEMALE("Жен.");

        private String description;

        Sex(String description) {
            this.description = description;
        }

        public static Sex getByDescription(String description) {
           return Arrays.stream(values()).filter((s) -> Objects.equals(description, s.getDescription())).findFirst().orElse(MALE);
        }

        public String getDescription() {
            return description;
        }
    }
}
