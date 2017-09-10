import {Routes} from '@angular/router';
import {WelcomeComponent} from './welcome/welcome.component';
import {InitDirsComponent} from './init-dirs/init-dirs.component';
import {Constants} from './shared/constants';

export const appRoutes: Routes = [
    {path: Constants.ROUTE_HOME, component: WelcomeComponent},
    {path: Constants.ROUTE_STEP_1, component: InitDirsComponent}
];