import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask';

import { AppDoraSharedModule } from '../../shared';
import {AutoCompleteModule} from 'primeng/autocomplete';

import {
    CheckoutService,
    CheckoutComponent,
    CheckoutDetailComponent,
    CheckoutDialogComponent,
    CheckoutDeletePopupComponent,
    CheckoutDeleteDialogComponent,
    checkoutRoute,
    checkoutPopupRoute,
    CheckoutResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...checkoutRoute,
    ...checkoutPopupRoute,
];

@NgModule({
    imports: [
        AppDoraSharedModule,
        FormsModule,
        AutoCompleteModule,
        TextMaskModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CheckoutComponent,
        CheckoutDetailComponent,
        CheckoutDialogComponent,
        CheckoutDeleteDialogComponent,
        CheckoutDeletePopupComponent,
    ],
    entryComponents: [
        CheckoutComponent,
        CheckoutDialogComponent,
        CheckoutDeleteDialogComponent,
        CheckoutDeletePopupComponent,
    ],
    providers: [
        CheckoutService,
        CheckoutResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraCheckoutModule {}
