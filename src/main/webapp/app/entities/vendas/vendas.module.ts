import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {CardModule} from 'primeng/card';
import {
    VendasService,
    VendasComponent,
    vendasRoute,
} from './';
import { CategoriaVendasComponent } from './categoria-vendas/categoria-vendas.component';
import {CommonModule} from '@angular/common';
import { ProdutosVendasComponent } from './produtos-vendas/produtos-vendas.component';
import { CarrinhoVendasComponent } from './carrinho-vendas/carrinho-vendas.component';
import { CarrinhoVendasItemComponent } from './carrinho-vendas-item/carrinho-vendas-item.component';
import { CarrinhoComponent } from './carrinho/carrinho.component';
import {ConfirmDialogModule} from "primeng/primeng";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OrderModule} from "./order/order.module";

const ENTITY_STATES = [
    ...vendasRoute,
];

@NgModule({
    imports: [
        CommonModule,
        CardModule,
        ConfirmDialogModule,
        FormsModule,
        OrderModule,
        ReactiveFormsModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VendasComponent,
        CategoriaVendasComponent,
        ProdutosVendasComponent,
        CarrinhoVendasComponent,
        CarrinhoVendasItemComponent,
        CarrinhoComponent,
    ],
    entryComponents: [
        VendasComponent,
    ],
    providers: [
        VendasService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports:[ReactiveFormsModule]
})
export class AppDoraVendasModule {}
