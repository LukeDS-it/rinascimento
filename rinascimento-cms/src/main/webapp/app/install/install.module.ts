import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {DashboardComponent} from './dashboard/dashboard.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {WelcomeComponent} from './welcome/welcome.component';
import {InitDirsComponent} from './init-dirs/init-dirs.component';
import {ComponentsModule} from './shared/components/components.module';
import {InstallDbComponent} from './install-db/install-db.component';
import {UserComponent} from './user/user.component';
import {SiteInfoComponent} from './site-info/site-info.component';
import {DoneComponent} from './done/done.component';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RlTagInputModule} from 'angular2-tag-input/dist';

@NgModule({
    declarations: [
        DashboardComponent,
        WelcomeComponent,
        InitDirsComponent,
        InstallDbComponent,
        UserComponent,
        SiteInfoComponent,
        DoneComponent
    ],
    imports: [
        RlTagInputModule,
        BrowserModule,
        ComponentsModule,
        CommonModule,
        FormsModule,
        RouterModule.forRoot(appRoutes, {useHash: true})
    ],
    providers: [],
    bootstrap: [DashboardComponent]
})
export class InstallModule {
}
