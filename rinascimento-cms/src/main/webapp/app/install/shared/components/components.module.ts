import {NgModule} from '@angular/core';
import {StepperComponent} from './stepper/stepper.component';
import {CommonModule} from '@angular/common';

@NgModule({
    declarations: [
        StepperComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        StepperComponent
    ]
})
export class ComponentsModule {

}