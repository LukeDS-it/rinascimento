import {Routes} from '@angular/router';
import {Constants} from './shared/constants';
import {WelcomeComponent} from './welcome/welcome.component';
import {InitDirsComponent} from './init-dirs/init-dirs.component';
import {InstallDbComponent} from './install-db/install-db.component';
import {UserComponent} from './user/user.component';
import {SiteInfoComponent} from './site-info/site-info.component';
import {DoneComponent} from './done/done.component';

export const appRoutes: Routes = [
    {path: Constants.ROUTE_HOME, component: WelcomeComponent},
    {path: Constants.ROUTE_STEP_1, component: InitDirsComponent},
    {path: Constants.ROUTE_STEP_2, component: InstallDbComponent},
    {path: Constants.ROUTE_STEP_3, component: SiteInfoComponent},
    {path: Constants.ROUTE_STEP_4, component: UserComponent},
    {path: Constants.ROUTE_STEP_5, component: DoneComponent}
];