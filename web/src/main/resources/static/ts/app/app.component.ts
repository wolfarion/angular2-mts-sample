import { Component, Input } from '@angular/core';

@Component({
    selector: 'user-service',
    templateUrl: 'app.component.html'
})
export class UserService {

    title = "Учетные записи пользователей";

    constructor () {
    }

}