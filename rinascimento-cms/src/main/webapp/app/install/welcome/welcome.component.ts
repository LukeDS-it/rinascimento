import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../shared/constants';

@Component({
    templateUrl: './welcome.component.html'
})
export class WelcomeComponent {

    constructor(private router: Router) {
    }

    doWork() {
        this.router.navigate([Constants.ROUTE_STEP_1]);
    }
}