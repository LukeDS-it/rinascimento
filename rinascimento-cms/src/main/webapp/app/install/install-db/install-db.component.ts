import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../shared/constants';
import {DatabaseInfo} from '../shared/model/DatabaseInfo';

@Component({
    templateUrl: './install-db.component.html'
})
export class InstallDbComponent {

    dbInfo: DatabaseInfo = new DatabaseInfo();

    constructor(private router: Router) {

    }

    doWork() {
        this.router.navigate([Constants.ROUTE_STEP_3]);
    }
}