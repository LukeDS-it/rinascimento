import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../../constants';

@Component({
    selector: 'stepper',
    templateUrl: './stepper.component.html'
})
export class StepperComponent {

    private SELECTED = 'btn-primary';
    private DONE = 'btn-success';
    private NEXT = 'btn-dark';

    constructor(private router: Router) {}

    buttonClass(step: number): string {
        switch(this.router.url) {
            case '/' + Constants.ROUTE_HOME:
                return this.switchButton(0, step);
            case '/' + Constants.ROUTE_STEP_1:
                return this.switchButton(1, step);
            case '/' + Constants.ROUTE_STEP_2:
                return this.switchButton(2, step);
            case '/' + Constants.ROUTE_STEP_3:
                return this.switchButton(3, step);
            case '/' + Constants.ROUTE_STEP_4:
                return this.switchButton(4, step);
            case '/' + Constants.ROUTE_STEP_5:
                return this.switchButton(5, step);
        }
        console.log(this.router.url);
        return '';
    }

    private switchButton(current: number, own: number): string {
        if (current < own) return this.NEXT;
        if (current == own) return this.SELECTED;
        return this.DONE;
    }

}