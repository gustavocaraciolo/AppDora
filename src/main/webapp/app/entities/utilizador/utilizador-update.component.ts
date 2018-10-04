import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUtilizador } from 'app/shared/model/utilizador.model';
import { UtilizadorService } from './utilizador.service';
import { IUser, UserService } from 'app/core';
import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from 'app/entities/portal';

@Component({
  selector: 'jhi-utilizador-update',
  templateUrl: './utilizador-update.component.html'
})
export class UtilizadorUpdateComponent implements OnInit {
  private _utilizador: IUtilizador;
  isSaving: boolean;

  users: IUser[];

  portals: IPortal[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private utilizadorService: UtilizadorService,
    private userService: UserService,
    private portalService: PortalService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ utilizador }) => {
      this.utilizador = utilizador;
    });
    this.userService.query().subscribe(
      (res: HttpResponse<IUser[]>) => {
        this.users = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.portalService.query().subscribe(
      (res: HttpResponse<IPortal[]>) => {
        this.portals = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.utilizador.id !== undefined) {
      this.subscribeToSaveResponse(this.utilizadorService.update(this.utilizador));
    } else {
      this.subscribeToSaveResponse(this.utilizadorService.create(this.utilizador));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUtilizador>>) {
    result.subscribe((res: HttpResponse<IUtilizador>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackPortalById(index: number, item: IPortal) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
  get utilizador() {
    return this._utilizador;
  }

  set utilizador(utilizador: IUtilizador) {
    this._utilizador = utilizador;
  }
}
