import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INoticia } from 'app/shared/model/noticia.model';

type EntityResponseType = HttpResponse<INoticia>;
type EntityArrayResponseType = HttpResponse<INoticia[]>;

@Injectable({ providedIn: 'root' })
export class NoticiaService {
  private resourceUrl = SERVER_API_URL + 'api/noticias';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/noticias';

  constructor(private http: HttpClient) {}

  create(noticia: INoticia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(noticia);
    return this.http
      .post<INoticia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(noticia: INoticia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(noticia);
    return this.http
      .put<INoticia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INoticia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INoticia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INoticia[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  private convertDateFromClient(noticia: INoticia): INoticia {
    const copy: INoticia = Object.assign({}, noticia, {
      dataInicio: noticia.dataInicio != null && noticia.dataInicio.isValid() ? noticia.dataInicio.toJSON() : null,
      dataFim: noticia.dataFim != null && noticia.dataFim.isValid() ? noticia.dataFim.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.dataInicio = res.body.dataInicio != null ? moment(res.body.dataInicio) : null;
    res.body.dataFim = res.body.dataFim != null ? moment(res.body.dataFim) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((noticia: INoticia) => {
      noticia.dataInicio = noticia.dataInicio != null ? moment(noticia.dataInicio) : null;
      noticia.dataFim = noticia.dataFim != null ? moment(noticia.dataFim) : null;
    });
    return res;
  }
}
