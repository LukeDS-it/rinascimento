import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../shared/constants';
import {SiteInfo} from '../shared/model/SiteInfo';

@Component({
    templateUrl: './site-info.component.html'
})
export class SiteInfoComponent {

    siteInfo: SiteInfo = new SiteInfo();

    constructor(private router: Router) {

    }

    doWork() {
        this.router.navigate([Constants.ROUTE_STEP_4]);
    }
}