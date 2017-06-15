import com.cbgr.Application;
import com.cbgr.assemble.user.UserAssembler;
import com.cbgr.dao.user.UserDao;
import com.cbgr.dto.user.UserDto;
import com.cbgr.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserAssembler userAssembler;

    @MockBean
    private UserDao userDao;

    @Test
    public void getAllUsersMustReturnCorrectResult() throws Exception {
        when(userDao.findAll()).thenReturn(Collections.singletonList(new User()));
        ResponseEntity<List> entity = restTemplate.getForEntity("/user-service/users", List.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().size()).isEqualTo(1);
    }

    @Test
    public void getUserByIdMustReturnCorrectResult() {
        User user = new User("123", "Пупкин Василий", "vpupkin", "123", LocalDate.of(1990, Month.JANUARY, 1), User.Sex.MALE);
        when(userDao.findById("123")).thenReturn(Optional.of(user));
        ResponseEntity<UserDto> entity = restTemplate.getForEntity("/user-service/users/123", UserDto.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(testDto());
    }

    @Test
    public void saveUserNotSuccessWhenDisplayNameIsNull() {
        UserDto user = new UserDto.Builder()
        .userId("345")
        .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
        .login("testLogin")
        .password("qwertyQWERTY123'")
        .sex(User.Sex.MALE.getDescription())
        .build();
        testFieldNotNull(user, "Отображаемое имя");
    }

    @Test
    public void saveUserNotSuccessWhenLoginIsNull() {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тест Тестов")
                .password("qwertyQWERTY123'")
                .sex(User.Sex.MALE.getDescription())
                .build();
       testFieldNotNull(user, "Идентификатор учетной записи");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordIsNull() {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тест Тестов")
                .login("test1")
                .sex(User.Sex.MALE.getDescription())
                .build();
        testFieldNotNull(user, "Пароль");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordNotContainsAtLeastOneLowerCaseCharacter() {
       testPasswordSecurity("QWERTY123'");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordNotContainsAtLeastOneUpperCaseCharacter() {
       testPasswordSecurity("qwerty123'");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordNotContainsAtLeastOneDigit() {
        testPasswordSecurity("qwertyQWERTY'");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordNotContainsAtLeastOneSpecialCharacter() {
        testPasswordSecurity("qwertyQWERETY123");
    }

    @Test
    public void saveUserNotSuccessWhenPasswordHasNotEightSymbolsLength() {
        testPasswordSecurity("qW1'");
    }

    @Test
    public void saveUserNotSuccessWhenDisplayNameHasLessThenThreeSymbolsLength() {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Те")
                .login("test1")
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        testFieldContentHasNotRequiredLength(user, "Отображаемое имя", 3, 50);
    }

    @Test
    public void saveUserNotSuccessWhenDisplayNameHasGreaterThanFiftySymbolsLength() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 51; i++) {
            strBuilder.append("a");
        }
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName(strBuilder.toString())
                .login("test1")
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        testFieldContentHasNotRequiredLength(user, "Отображаемое имя", 3, 50);
    }

    @Test
    public void saveUserNotSuccessWhenLoginHasLessThenThreeSymbolsLength() {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тестов Тест")
                .login("te")
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        testFieldContentHasNotRequiredLength(user, "Идентификатор учетной записи", 3, 15);
    }

    @Test
    public void saveUserNotSuccessWhenLoginGreaterThanFifteenSymbolsLength() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            strBuilder.append("a");
        }
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName(strBuilder.toString())
                .login(strBuilder.toString())
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        testFieldContentHasNotRequiredLength(user, "Идентификатор учетной записи", 3, 15);
    }

    @Test
    public void saveUserNotSuccessWhenUserWithSameLoginExists() {
        User user = new User();
        user.setLogin("vpupkin");
        when(userDao.findAll()).thenReturn(Collections.singletonList(user));
        UserDto userDto = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тест Тестов")
                .login("vpupkin")
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        ResponseEntity<String> entity = restTemplate.postForEntity("/user-service/users", userDto, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isEqualTo("Пользователь с указанным логином уже существует в системе");
    }

    @Test
    public void saveUserSuccessWhenAllRequiredFieldsFilledProperly() {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тест Тестов")
                .login("test1")
                .password("qwertyQWERTY123'!")
                .sex(User.Sex.MALE.getDescription())
                .build();
        doNothing().when(userDao).save(userAssembler.fromDto(user));
        ResponseEntity<String> entity = restTemplate.postForEntity("/user-service/users", user, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("Пользователь успешно сохранен");
    }

  
    private void testFieldContentHasNotRequiredLength(UserDto userDto, String fieldName, int minLength, int maxLength) {
        doNothing().when(userDao).save(userAssembler.fromDto(userDto));
        ResponseEntity<String> entity = restTemplate.postForEntity("/user-service/users", userDto, String.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody())
                .isEqualTo(
                        String.format("Поле '%s' должно быть не менее %s и не более %s символов длиной",
                                fieldName, minLength, maxLength));
    }

    private void testPasswordSecurity(String password) {
        UserDto user = new UserDto.Builder()
                .userId("345")
                .birthDate(LocalDate.of(2017, Month.JANUARY, 1))
                .displayName("Тест Тестов")
                .login("test1")
                .password(password)
                .sex(User.Sex.MALE.getDescription())
                .build();
        ResponseEntity<String> entity = restTemplate.postForEntity("/user-service/users", user, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isEqualTo("Пароль не удовлетворяет условиям безопасности");
    }

    private void testFieldNotNull(UserDto userDto, String fieldName) {
        ResponseEntity<String> entity = restTemplate.postForEntity("/user-service/users", userDto, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isEqualTo(String.format("Поле '%s' не должно быть пустым", fieldName));
    }

    private static UserDto testDto() {
        return new UserDto.Builder()
                .userId("123")
                .displayName("Пупкин Василий")
                .login("vpupkin")
                .password("123")
                .birthDate(LocalDate.of(1990, Month.JANUARY, 1))
                .sex(User.Sex.MALE.getDescription())
                .build();
    }
}
