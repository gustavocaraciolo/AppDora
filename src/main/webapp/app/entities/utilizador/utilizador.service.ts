import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUtilizador } from 'app/shared/model/utilizador.model';

type EntityResponseType = HttpResponse<IUtilizador>;
type EntityArrayResponseType = HttpResponse<IUtilizador[]>;

@Injectable({ providedIn: 'root' })
export class UtilizadorService {
  private resourceUrl = SERVER_API_URL + 'api/utilizadors';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/utilizadors';

  constructor(private http: HttpClient) {}

  create(utilizador: IUtilizador): Observable<EntityResponseType> {
    return this.http.post<IUtilizador>(this.resourceUrl, utilizador, { observe: 'response' });
  }

  update(utilizador: IUtilizador): Observable<EntityResponseType> {
    return this.http.put<IUtilizador>(this.resourceUrl, utilizador, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUtilizador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUtilizador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUtilizador[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
