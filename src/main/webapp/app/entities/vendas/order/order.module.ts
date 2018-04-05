import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule, Routes} from '@angular/router'
import {OrderComponent} from './order.component'
import {OrderItemsComponent} from './order-items/order-items.component'
import {DeliveryCostsComponent} from './delivery-costs/delivery-costs.component'
import {SharedModule} from "primeng/shared";
import {CommonModule} from '@angular/common';

import {AutoCompleteModule} from 'primeng/components/autocomplete/autocomplete';
import {GrowlModule} from 'primeng/primeng';
import {PanelModule} from 'primeng/components/panel/panel';
import {DropdownModule} from 'primeng/components/dropdown/dropdown';
import {InputTextModule} from 'primeng/components/inputtext/inputtext';
import {InputMaskModule} from 'primeng/components/inputmask/inputmask';
import {InputTextareaModule} from 'primeng/components/inputtextarea/inputtextarea';
import {ButtonModule} from 'primeng/components/button/button';
import {CheckboxModule} from 'primeng/components/checkbox/checkbox';
import {TriStateCheckboxModule} from 'primeng/components/tristatecheckbox/tristatecheckbox';
import {BrowserModule} from '@angular/platform-browser';
import {APP_BASE_HREF} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';
import { AppDoraSharedModule } from '../../../shared';
import {MultiSelectModule} from 'primeng/components/multiselect/multiselect';
import {SelectButtonModule} from 'primeng/components/selectbutton/selectbutton';
import {CheckoutComponent, CheckoutService} from "../../checkout";

const ROUTES: Routes = [
    {path: '', component: OrderComponent}
]

@NgModule({
    declarations: [OrderComponent, OrderItemsComponent, DeliveryCostsComponent],
    imports: [SharedModule, RouterModule.forChild(ROUTES), FormsModule, ReactiveFormsModule, CommonModule,BrowserModule,
        FormsModule,
        BrowserAnimationsModule,
        CheckboxModule,
        TriStateCheckboxModule,
        WizardModule,
        ReactiveFormsModule,
        PanelModule,
        DropdownModule,
        InputTextModule,
        InputMaskModule,
        InputTextareaModule,
        ButtonModule,
        AutoCompleteModule,
        GrowlModule,
        MultiSelectModule,
        SelectButtonModule,
    ]
})
export class OrderModule {
}
