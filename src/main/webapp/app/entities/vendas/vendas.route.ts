import { Routes } from '@angular/router';

import { VendasComponent } from './vendas.component';
import {ProdutosVendasComponent} from "./produtos-vendas/produtos-vendas.component";
import {CarrinhoVendasComponent} from "./carrinho-vendas/carrinho-vendas.component";
import {OrderModule} from "./order/order.module";
import {OrderComponent} from "./order/order.component";

export const vendasRoute: Routes = [
    {
        path: 'categorias',
        component: VendasComponent,
        data: {
            authorities: [],
            pageTitle: 'appDoraApp.vendas.home.title'
        }
    },
    {   path: 'comercializar',
        component: ProdutosVendasComponent,
        children: [
            {path: '', redirectTo: 'produto', pathMatch: 'full'},
            {path: 'produto', component: CarrinhoVendasComponent}
        ],
        data: {
            authorities: [],
            pageTitle: 'appDoraApp.vendas.home.title'
        }
    },
    {   path: 'order',
        component: OrderComponent,
        data: {
            authorities: [],
            pageTitle: 'appDoraApp.vendas.home.title'
        }
    },
];
