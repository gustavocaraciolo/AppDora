import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask';

import { AppDoraSharedModule } from '../../shared';
import {
    CheckoutService,
    CheckoutPopupService,
    CheckoutComponent,
    CheckoutDetailComponent,
    CheckoutDialogComponent,
    CheckoutPopupComponent,
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
        TextMaskModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CheckoutComponent,
        CheckoutDetailComponent,
        CheckoutDialogComponent,
        CheckoutDeleteDialogComponent,
        CheckoutPopupComponent,
        CheckoutDeletePopupComponent,
    ],
    entryComponents: [
        CheckoutComponent,
        CheckoutDialogComponent,
        CheckoutPopupComponent,
        CheckoutDeleteDialogComponent,
        CheckoutDeletePopupComponent,
    ],
    providers: [
        CheckoutService,
        CheckoutPopupService,
        CheckoutResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraCheckoutModule {}
