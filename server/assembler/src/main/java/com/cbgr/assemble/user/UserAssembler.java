package com.cbgr.assemble.user;

import com.cbgr.assemble.Assembler;
import com.cbgr.dto.user.UserDto;
import com.cbgr.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Ассемблер сущности пользователя.
 */
@Component
public class UserAssembler implements Assembler<UserDto, User> {

    @Override
    public UserDto toDto(User model) {
        Objects.requireNonNull(model);

        return new UserDto.Builder()
                .userId(model.getId())
                .displayName(model.getDisplayName())
                .login(model.getLogin())
                .password(model.getPassword())
                .birthDate(model.getBirthDate())
                .sex(model.getSex() != null ? model.getSex().getDescription() : null)
                .build();
    }

    @Override
    public User fromDto(UserDto dto) {
        Objects.requireNonNull(dto);

        return new User(dto.getUserId(),
                dto.getDisplayName(),
                dto.getLogin(),
                dto.getPassword(),
                dto.getBirthDate(),
                User.Sex.getByDescription(dto.getSex()));
    }
}
