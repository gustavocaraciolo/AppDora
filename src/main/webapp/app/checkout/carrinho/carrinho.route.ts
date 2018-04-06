import { Routes } from '@angular/router';
import {UserRouteAccessService} from "../../shared";
import {CarrinhoComponent} from "./carrinho.component";
import {OrderComponent} from "../order/order.component";


export const carrinhoCheckoutRoute: Routes = [
    {
        path: 'carrinho',
        component: CarrinhoComponent,
        data: {
            authorities: [],
            pageTitle: 'primeng.inputs.autocomplete.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pagamento',
        component: OrderComponent,
        data: {
            authorities: [],
            pageTitle: 'appDoraApp.vendas.home.title'
        }
    },
];
