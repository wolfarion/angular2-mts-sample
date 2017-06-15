import { BrowserModule } from '@angular/platform-browser'
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { UserService } from './app.component';
import { UserDataService } from './services/user.service';
import { UserListComponent } from "./userList/userList.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule
    ],
    providers: [
        UserDataService
    ],
    declarations: [
        UserService,
        UserListComponent
    ],
    bootstrap: [
        UserService
    ]
})
export class UserServiceModule { }