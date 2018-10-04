import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from './portal.service';
import { IUtilizador } from 'app/shared/model/utilizador.model';
import { UtilizadorService } from 'app/entities/utilizador';

@Component({
  selector: 'jhi-portal-update',
  templateUrl: './portal-update.component.html'
})
export class PortalUpdateComponent implements OnInit {
  private _portal: IPortal;
  isSaving: boolean;

  utilizadors: IUtilizador[];
  dataAtivacao: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private portalService: PortalService,
    private utilizadorService: UtilizadorService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ portal }) => {
      this.portal = portal;
    });
    this.utilizadorService.query().subscribe(
      (res: HttpResponse<IUtilizador[]>) => {
        this.utilizadors = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.portal.dataAtivacao = moment(this.dataAtivacao, DATE_TIME_FORMAT);
    if (this.portal.id !== undefined) {
      this.subscribeToSaveResponse(this.portalService.update(this.portal));
    } else {
      this.subscribeToSaveResponse(this.portalService.create(this.portal));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPortal>>) {
    result.subscribe((res: HttpResponse<IPortal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUtilizadorById(index: number, item: IUtilizador) {
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
  get portal() {
    return this._portal;
  }

  set portal(portal: IPortal) {
    this._portal = portal;
    this.dataAtivacao = moment(portal.dataAtivacao).format(DATE_TIME_FORMAT);
  }
}
