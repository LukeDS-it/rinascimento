import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../shared/constants';
import {UserInfo} from '../shared/model/UserInfo';

@Component({
    templateUrl: './user.component.html'
})
export class UserComponent {

    user: UserInfo = new UserInfo();

    constructor(private router: Router) {

    }

    doWork() {
        this.router.navigate([Constants.ROUTE_STEP_4]);
    }
}