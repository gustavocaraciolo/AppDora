import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppDoraCarrinhoModule} from "./carrinho/carrinho.module";
import {CarrinhoItemComponent} from './carrinho-item/carrinho-item.component';
import {CarrinhoFrameComponent} from './carrinho-frame/carrinho-frame.component';
import {CarrinhoComponent} from "./carrinho";
import {CarrinhoFrameService} from "./carrinho-frame/carrinho-frame.service";
import {ConfirmDialogModule} from "primeng/primeng";
import {CardModule} from 'primeng/card';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule } from '@angular/router';
import {OrderModule} from "./order/order.module";
import {OrderService} from "./order/order.service";

@NgModule({
    imports: [
        CommonModule,
        AppDoraCarrinhoModule,
        CardModule,
        ConfirmDialogModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        OrderModule
    ],
    declarations: [CarrinhoItemComponent,
        CarrinhoFrameComponent,
        CarrinhoComponent
    ],
    entryComponents: [],
    providers: [CarrinhoFrameService, OrderService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CheckoutModule {
}
