import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { INoticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';
import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from 'app/entities/portal';

@Component({
  selector: 'jhi-noticia-update',
  templateUrl: './noticia-update.component.html'
})
export class NoticiaUpdateComponent implements OnInit {
  private _noticia: INoticia;
  isSaving: boolean;

  portals: IPortal[];
  dataInicio: string;
  dataFim: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private noticiaService: NoticiaService,
    private portalService: PortalService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ noticia }) => {
      this.noticia = noticia;
    });
    this.portalService.query({ filter: 'noticia-is-null' }).subscribe(
      (res: HttpResponse<IPortal[]>) => {
        if (!this.noticia.portal || !this.noticia.portal.id) {
          this.portals = res.body;
        } else {
          this.portalService.find(this.noticia.portal.id).subscribe(
            (subRes: HttpResponse<IPortal>) => {
              this.portals = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.noticia.dataInicio = moment(this.dataInicio, DATE_TIME_FORMAT);
    this.noticia.dataFim = moment(this.dataFim, DATE_TIME_FORMAT);
    if (this.noticia.id !== undefined) {
      this.subscribeToSaveResponse(this.noticiaService.update(this.noticia));
    } else {
      this.subscribeToSaveResponse(this.noticiaService.create(this.noticia));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<INoticia>>) {
    result.subscribe((res: HttpResponse<INoticia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPortalById(index: number, item: IPortal) {
    return item.id;
  }
  get noticia() {
    return this._noticia;
  }

  set noticia(noticia: INoticia) {
    this._noticia = noticia;
    this.dataInicio = moment(noticia.dataInicio).format(DATE_TIME_FORMAT);
    this.dataFim = moment(noticia.dataFim).format(DATE_TIME_FORMAT);
  }
}
