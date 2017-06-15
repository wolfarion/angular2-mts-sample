import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserDataService } from "../services/user.service";
import { User } from "./user";

@Component({
    selector: 'userList',
    templateUrl: 'userList.component.html'
})
export class UserListComponent implements OnInit {

    resultLastOperation = {
        status: null,
        text: null
    };

    sexList = ["Муж.", "Жен."];

    userList: User[] = [];

    constructor (private _userDataService: UserDataService) {
    }

    ngOnInit() {
        this._userDataService.getAllUsers()
            .subscribe(usersSuccess => {
                let resultList = usersSuccess.json();

                let list :User[] = [];
                for (let index in resultList) {
                    let user : User = resultList[index];
                    list.push(new User({type: 'change', userId: user.userId, displayName: user.displayName, login: user.login, password: user.password, birthDate: user.birthDate, sex: user.sex}));
                }

                this.userList = list;
                this.onDoneOperation("success", "Успешно получен список пользователей.");
            }, error => this.onDoneOperation("error", `Ошибка при запросе пользователей: ${error._body}`));
    }

    /* добавляем нового пользователя в список */
    addUser() {
        let newUser = new User({type: 'new'});
        this.userList.push(newUser);
    }

    /* Сохранение изменений */
    updateUser(user: User) {
        let userForResponse = {
            userId: user.userId,
            displayName: user.displayName,
            login: user.login,
            password: user.password,
            birthDate: user.birthDate,
            sex: user.sex
        };

        if (user.isNew) {
            this.createUser(userForResponse);
        } else {
            this.saveUser(userForResponse);
        }
    }

    /* Добавление нового пользователя */
    createUser(user) {
        if (this.checkDateField(user.birthDate)) {
            this._userDataService.createUser(user)
                .subscribe(success => {
                    //выставим новому пользователю id
                    let id = (JSON.parse(success["_body"])).id;
                    let index = this.userList.findIndex(item => item.login == user.login);
                    this.userList[index].userId = id;

                    this.userList[index].isDirty = false;
                    this.userList[index].isNew = false;

                    this.onDoneOperation("success", `Успешно добавлен пользователь с логином "${user.login}".`);
                }, error => this.onDoneOperation("error", `Ошибка при добавлении пользователя с логином "${user.login}": ${error._body}`));
        } else {
            this.onDoneOperation("error", `Ошибка при добавлении пользователя с логином "${user.login}": Поле "Дата рождения" должно иметь вид "01.01.1990"`);
        }
    }

    /* Обновление информации о пользователе */
    saveUser(user) {
        if (this.checkDateField(user.birthDate)) {
            this._userDataService.saveUser(user)
                .subscribe(success => {
                    let index = this.userList.findIndex(item => item.login == user.login);
                    this.userList[index].isDirty = false;

                    this.onDoneOperation("success", `Успешно обновлен пользователь с логином "${user.login}".`);
                }, error => this.onDoneOperation("error", `Ошибка при обновлении пользователя с логином "${user.login}": ${error._body}`));
        } else {
            this.onDoneOperation("error", `Ошибка при обновлении пользователя с логином "${user.login}": Поле "Дата рождения" должно иметь вид "01.01.1990"`);
        }
    }

    /* Удаление пользователя */
    deleteUser(id) {
        let index = this.userList.findIndex(item => item.userId == id);
        let login = this.userList[index].login;
        this._userDataService.deleteUser(id)
            .subscribe(success => {
                this.onDoneOperation("success", `Успешно удален пользователь с логином "${login}".`);
                this.userList.splice(index, 1)
            }, error => this.onDoneOperation("error", `Ошибка при добавлении пользователя с логином "${login}": ${error._body}`));
    }

    /* Помечает, что запись была изменена и требует сохранения */
    changeUserValue(item, property, value) {
        item[property] = value;
        item.isDirty = true;
    }

    /* Устанавливает результат выполнения последней операции */
    onDoneOperation(status, text) {
        this.resultLastOperation.status = status;
        this.resultLastOperation.text = text;
    }

    /* Проверяет на корректность ввода поле Дата рождения */
    checkDateField(date) {
        let pattern = /^\d{2}.\d{2}.\d{4}$/;
        return pattern.test(date) ? true : false;
    }

}