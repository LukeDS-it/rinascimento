import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {DashboardComponent} from './dashboard/dashboard.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {WelcomeComponent} from './welcome/welcome.component';
import {InitDirsComponent} from './init-dirs/init-dirs.component';
import {ComponentsModule} from './shared/components/components.module';

@NgModule({
    declarations: [
        DashboardComponent,
        WelcomeComponent,
        InitDirsComponent
    ],
    imports: [
        BrowserModule,
        ComponentsModule,
        RouterModule.forRoot(appRoutes, {useHash: true})
    ],
    providers: [],
    bootstrap: [DashboardComponent]
})
export class InstallModule {
}
