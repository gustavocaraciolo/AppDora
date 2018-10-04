import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Noticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';
import { NoticiaComponent } from './noticia.component';
import { NoticiaDetailComponent } from './noticia-detail.component';
import { NoticiaUpdateComponent } from './noticia-update.component';
import { NoticiaDeletePopupComponent } from './noticia-delete-dialog.component';
import { INoticia } from 'app/shared/model/noticia.model';

@Injectable({ providedIn: 'root' })
export class NoticiaResolve implements Resolve<INoticia> {
  constructor(private service: NoticiaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((noticia: HttpResponse<Noticia>) => noticia.body));
    }
    return of(new Noticia());
  }
}

export const noticiaRoute: Routes = [
  {
    path: 'noticia',
    component: NoticiaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'appDoraApp.noticia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'noticia/:id/view',
    component: NoticiaDetailComponent,
    resolve: {
      noticia: NoticiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.noticia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'noticia/new',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.noticia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'noticia/:id/edit',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.noticia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const noticiaPopupRoute: Routes = [
  {
    path: 'noticia/:id/delete',
    component: NoticiaDeletePopupComponent,
    resolve: {
      noticia: NoticiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appDoraApp.noticia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
