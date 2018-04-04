import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ItensCheckoutComponent } from './itens-checkout.component';
import { ItensCheckoutDetailComponent } from './itens-checkout-detail.component';
import { ItensCheckoutPopupComponent } from './itens-checkout-dialog.component';
import { ItensCheckoutDeletePopupComponent } from './itens-checkout-delete-dialog.component';

@Injectable()
export class ItensCheckoutResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const itensCheckoutRoute: Routes = [
    {
        path: 'itens-checkout',
        component: ItensCheckoutComponent,
        resolve: {
            'pagingParams': ItensCheckoutResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.itensCheckout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'itens-checkout/:id',
        component: ItensCheckoutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.itensCheckout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itensCheckoutPopupRoute: Routes = [
    {
        path: 'itens-checkout-new',
        component: ItensCheckoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.itensCheckout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'itens-checkout/:id/edit',
        component: ItensCheckoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.itensCheckout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'itens-checkout/:id/delete',
        component: ItensCheckoutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.itensCheckout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
