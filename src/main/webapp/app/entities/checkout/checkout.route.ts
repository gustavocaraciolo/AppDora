import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CheckoutComponent } from './checkout.component';
import { CheckoutDetailComponent } from './checkout-detail.component';
import {CheckoutDeletePopupComponent} from './checkout-delete-dialog.component';
import { CheckoutService } from './checkout.service';
import { Checkout } from './checkout.model';
import { CheckoutDialogComponent } from '.';

@Injectable()
export class CheckoutResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil, private service: CheckoutService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
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
        path: 'checkout/:id/view',
        resolve: {
            checkout: CheckoutResolvePagingParams
        },
        component: CheckoutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'checkout/new',
        component: CheckoutDialogComponent,
        resolve: {
            checkout: CheckoutResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'checkout/:id/edit',
        component: CheckoutDialogComponent,
        resolve: {
            checkout: CheckoutResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.checkout.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
];

export const checkoutPopupRoute: Routes = [
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
