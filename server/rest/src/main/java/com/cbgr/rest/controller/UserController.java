package com.cbgr.rest.controller;

import com.cbgr.assemble.user.UserAssembler;
import com.cbgr.business.user.UserService;
import com.cbgr.dto.operation.OperationResult;
import com.cbgr.dto.user.UserDto;
import com.cbgr.entity.user.User;
import com.cbgr.rest.config.JerseyConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Path("/users")
@Api(value = "User resource", produces = JerseyConfig.JSON_MEDIA_TYPE)
@Produces(JerseyConfig.JSON_MEDIA_TYPE)
@Consumes(JerseyConfig.JSON_MEDIA_TYPE)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAssembler userAssembler;

    @GET()
    @ApiOperation(value = "Загрузить список всех пользователей системы.", httpMethod = HttpMethod.GET, response = List.class)
    public List<UserDto> getUsersList() {
        return userService.findAll().stream().map(user -> userAssembler.toDto(user)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Найти пользователя по его идентификатору.", httpMethod = HttpMethod.GET, response = UserDto.class)
    public UserDto getUser(@PathParam("id") String id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isPresent()) {
            return userAssembler.toDto(optional.get());
        }
        return null;
    }

    @POST
    @ApiOperation(value = "Создать пользователя.", httpMethod = HttpMethod.POST)
    public Response createUser(@Valid UserDto user) {
        String id = userService.save(userAssembler.fromDto(user));
        return Response.ok(new OperationResult(id, "Пользователь успешно сохранен")).build();
    }

    @PUT
    @ApiOperation(value = "Обновить данные пользователя.", httpMethod = HttpMethod.PUT)
    public Response updateUser(@Valid UserDto user) {
        userService.update(userAssembler.fromDto(user));
        return Response.ok("Данные пользователя успешно обновлены").type(MediaType.TEXT_PLAIN).build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Удалить пользователя.", httpMethod = HttpMethod.DELETE)
    public void deleteUser(@PathParam("id") String id) {
        userService.delete(id);
    }
}
