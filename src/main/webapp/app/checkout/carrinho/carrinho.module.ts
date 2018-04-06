import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {APP_BASE_HREF} from '@angular/common';

import {AppDoraSharedModule} from "../../shared";

import {
    carrinhoCheckoutRoute
} from './';

const primeng_STATES = [
    ...carrinhoCheckoutRoute,
];

@NgModule({
    imports: [
        AppDoraSharedModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [

    ],
    providers: [{provide: APP_BASE_HREF, useValue: '/'}],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraCarrinhoModule {}
