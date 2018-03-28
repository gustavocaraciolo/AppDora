import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './cliente-detail.component';
import { ClientePopupComponent } from './cliente-dialog.component';
import { ClienteDeletePopupComponent } from './cliente-delete-dialog.component';

@Injectable()
export class ClienteResolvePagingParams implements Resolve<any> {

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

export const clienteRoute: Routes = [
    {
        path: 'cliente',
        component: ClienteComponent,
        resolve: {
            'pagingParams': ClienteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cliente/:id',
        component: ClienteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientePopupRoute: Routes = [
    {
        path: 'cliente-new',
        component: ClientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cliente/:id/edit',
        component: ClientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cliente/:id/delete',
        component: ClienteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
