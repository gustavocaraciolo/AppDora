import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppDoraSharedModule } from '../../shared';
import {
    ItensCheckoutService,
    ItensCheckoutPopupService,
    ItensCheckoutComponent,
    ItensCheckoutDetailComponent,
    ItensCheckoutDialogComponent,
    ItensCheckoutPopupComponent,
    ItensCheckoutDeletePopupComponent,
    ItensCheckoutDeleteDialogComponent,
    itensCheckoutRoute,
    itensCheckoutPopupRoute,
    ItensCheckoutResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...itensCheckoutRoute,
    ...itensCheckoutPopupRoute,
];

@NgModule({
    imports: [
        AppDoraSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ItensCheckoutComponent,
        ItensCheckoutDetailComponent,
        ItensCheckoutDialogComponent,
        ItensCheckoutDeleteDialogComponent,
        ItensCheckoutPopupComponent,
        ItensCheckoutDeletePopupComponent,
    ],
    entryComponents: [
        ItensCheckoutComponent,
        ItensCheckoutDialogComponent,
        ItensCheckoutPopupComponent,
        ItensCheckoutDeleteDialogComponent,
        ItensCheckoutDeletePopupComponent,
    ],
    providers: [
        ItensCheckoutService,
        ItensCheckoutPopupService,
        ItensCheckoutResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraItensCheckoutModule {}
