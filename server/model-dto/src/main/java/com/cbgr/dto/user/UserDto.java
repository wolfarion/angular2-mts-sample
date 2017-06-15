package com.cbgr.dto.user;

import com.cbgr.dto.Dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO модели пользователя.
 */
public class UserDto implements Dto{

    /** Id пользователя. */
    private final String userId;

    /** Отображаемое имя. */
    @NotNull(message = "Поле 'Отображаемое имя' не должно быть пустым")
    @Size(min = 3, max = 50, message = "Поле 'Отображаемое имя' должно быть не менее 3 и не более 50 символов длиной")
    private final String displayName;

    /** Идентификатор учетной записи. */
    @NotNull(message = "Поле 'Идентификатор учетной записи' не должно быть пустым")
    @Size(min = 3, max = 15, message = "Поле 'Идентификатор учетной записи' должно быть не менее 3 и не более 15 символов длиной")
    private final String login;

    /** Пароль. */
    @NotNull(message = "Поле 'Пароль' не должно быть пустым")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+='])(?=\\S+$).{8,}$", message = "Пароль не удовлетворяет условиям безопасности")
    private final String password;

    /** Дата рождения. */
    @NotNull(message = "Поле 'Дата рождения' не должно быть пустым")
    @JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "dd.MM.yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate birthDate;

    /** Пол. */
    private final String sex;

    private UserDto(Builder builder) {
        userId = builder.userId;
        displayName = builder.displayName;
        login = builder.login;
        password = builder.password;
        birthDate = builder.birthDate;
        sex = builder.sex;
    }

    // for testing
    @JsonCreator
    private UserDto(@JsonProperty("userId") String userId,
                    @JsonProperty("displayName")String displayName,
                    @JsonProperty("login") String login,
                    @JsonProperty("password") String password,
                    @JsonProperty("birthDate") LocalDate birthDate,
                    @JsonProperty("sex") String sex) {
        this.userId = userId;
        this.displayName = displayName;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) &&
                Objects.equals(displayName, userDto.displayName) &&
                Objects.equals(login, userDto.login) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(birthDate, userDto.birthDate) &&
                Objects.equals(sex, userDto.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, login, password, birthDate, sex);
    }

    public static class Builder {

        private String userId;

        private String displayName;

        private String login;

        private String password;

        private LocalDate birthDate;

        private String sex;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
