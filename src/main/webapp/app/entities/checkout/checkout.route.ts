import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CheckoutComponent } from './checkout.component';
import { CheckoutDetailComponent } from './checkout-detail.component';
import { CheckoutPopupComponent } from './checkout-dialog.component';
import { CheckoutDeletePopupComponent } from './checkout-delete-dialog.component';

@Injectable()
export class CheckoutResolvePagingParams implements Resolve<any> {

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

export const checkoutRoute: Routes = [
    {
        path: 'checkout',
        component: CheckoutComponent,
        resolve: {
            'pagingParams': CheckoutResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'checkout/:id',
        component: CheckoutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const checkoutPopupRoute: Routes = [
    {
        path: 'checkout-new',
        component: CheckoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'checkout/:id/edit',
        component: CheckoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'checkout/:id/delete',
        component: CheckoutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
