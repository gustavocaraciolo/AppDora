import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProdutoComponent } from './produto.component';
import { ProdutoDetailComponent } from './produto-detail.component';
import { ProdutoPopupComponent } from './produto-dialog.component';
import { ProdutoDeletePopupComponent } from './produto-delete-dialog.component';

@Injectable()
export class ProdutoResolvePagingParams implements Resolve<any> {

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

export const produtoRoute: Routes = [
    {
        path: 'produto',
        component: ProdutoComponent,
        resolve: {
            'pagingParams': ProdutoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.produto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'produto/:id',
        component: ProdutoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.produto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const produtoPopupRoute: Routes = [
    {
        path: 'produto-new',
        component: ProdutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.produto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produto/:id/edit',
        component: ProdutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.produto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produto/:id/delete',
        component: ProdutoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appDoraApp.produto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
