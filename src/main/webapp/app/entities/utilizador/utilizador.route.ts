import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Utilizador } from 'app/shared/model/utilizador.model';
import { UtilizadorService } from './utilizador.service';
import { UtilizadorComponent } from './utilizador.component';
import { UtilizadorDetailComponent } from './utilizador-detail.component';
import { UtilizadorUpdateComponent } from './utilizador-update.component';
import { UtilizadorDeletePopupComponent } from './utilizador-delete-dialog.component';
import { IUtilizador } from 'app/shared/model/utilizador.model';

@Injectable({ providedIn: 'root' })
export class UtilizadorResolve implements Resolve<IUtilizador> {
  constructor(private service: UtilizadorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((utilizador: HttpResponse<Utilizador>) => utilizador.body));
    }
    return of(new Utilizador());
  }
}

export const utilizadorRoute: Routes = [
  {
    path: 'utilizador',
    component: UtilizadorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'appDoraApp.utilizador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'utilizador/:id/view',
    component: UtilizadorDetailComponent,
    resolve: {
      utilizador: UtilizadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.utilizador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'utilizador/new',
    component: UtilizadorUpdateComponent,
    resolve: {
      utilizador: UtilizadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.utilizador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'utilizador/:id/edit',
    component: UtilizadorUpdateComponent,
    resolve: {
      utilizador: UtilizadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.utilizador.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const utilizadorPopupRoute: Routes = [
  {
    path: 'utilizador/:id/delete',
    component: UtilizadorDeletePopupComponent,
    resolve: {
      utilizador: UtilizadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.utilizador.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
