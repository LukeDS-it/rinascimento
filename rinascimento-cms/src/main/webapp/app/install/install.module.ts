import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {DashboardComponent} from './dashboard/dashboard.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {WelcomeComponent} from './welcome/welcome.component';

@NgModule({
    declarations: [
        DashboardComponent,
        WelcomeComponent
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes)
    ],
    providers: [],
    bootstrap: [DashboardComponent]
})
export class InstallModule {
}
