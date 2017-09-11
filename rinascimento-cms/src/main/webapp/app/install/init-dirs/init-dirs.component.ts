import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../shared/constants';

@Component({
    templateUrl: './init-dirs.component.html'
})
export class InitDirsComponent {
    baseDirectory: string;

    constructor(private router: Router) {

    }

    doWork() {
        this.router.navigate([Constants.ROUTE_STEP_2]);
    }

}