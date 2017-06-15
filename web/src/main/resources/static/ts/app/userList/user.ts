export class User {
    userId: string;
    displayName: string;
    login: string;
    password: string;
    birthDate: string;
    sex: string;

    /* Дополнительные поля */

    isDirty: boolean;
    isNew: boolean;

    /* Конструктор для добавления нового пользователя */
    constructor(x: { type:string })
    /* Конструктор для добавления существующего пользователя */
    constructor(x: { type:string, userId:string, displayName:string, login:string, password:string, birthDate:string, sex:string })
    constructor(x) {
        if (x.type === 'new') {
            this.userId = '';
            this.displayName = '';
            this.login = '';
            this.password = '';
            this.birthDate = '';
            this.sex = 'Муж.';

            this.isDirty = true;
            this.isNew = true;
        } else {
            this.userId = x.userId;
            this.displayName = x.displayName;
            this.login = x.login;
            this.password = x.password;
            this.birthDate = x.birthDate;
            this.sex = x.sex;

            this.isDirty = false;
            this.isNew = false;
        }
    }

}