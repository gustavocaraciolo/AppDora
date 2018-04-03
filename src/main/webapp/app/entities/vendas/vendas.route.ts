import { Routes } from '@angular/router';

import { VendasComponent } from './vendas.component';
import {ProdutosVendasComponent} from "./produtos-vendas/produtos-vendas.component";
import {CarrinhoVendasComponent} from "./carrinho-vendas/carrinho-vendas.component";

export const vendasRoute: Routes = [
    {
        path: 'categorias',
        component: VendasComponent,
        data: {
            authorities: [],
            pageTitle: 'appDoraApp.vendas.home.title'
        }
    },
    {   path: 'categorias/:id',
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
];
