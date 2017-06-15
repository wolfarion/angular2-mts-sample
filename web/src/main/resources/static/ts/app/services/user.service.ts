import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';

@Injectable()
export class UserDataService {

    API_URI = 'http://localhost:8081/user-service/users';

    constructor(private _http: Http) {

    }

    getAllUsers() {
        return this._http.get(this.API_URI);
    }

    createUser(user) {
        return this._http.post(this.API_URI, user);
    }

    saveUser(user) {
        return this._http.put(this.API_URI, user);
    }

    deleteUser(id) {
        return this._http.delete(this.API_URI + `/${id}`);
    }
}