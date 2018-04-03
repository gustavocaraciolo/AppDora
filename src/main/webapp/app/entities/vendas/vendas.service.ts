import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Vendas } from './vendas.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Vendas>;

@Injectable()
export class VendasService {

    private resourceUrl =  SERVER_API_URL + 'api/vendas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/vendas';

    constructor(private http: HttpClient) { }

    create(vendas: Vendas): Observable<EntityResponseType> {
        const copy = this.convert(vendas);
        return this.http.post<Vendas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vendas: Vendas): Observable<EntityResponseType> {
        const copy = this.convert(vendas);
        return this.http.put<Vendas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Vendas>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Vendas[]>> {
        const options = createRequestOption(req);
        return this.http.get<Vendas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Vendas[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Vendas[]>> {
        const options = createRequestOption(req);
        return this.http.get<Vendas[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Vendas[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Vendas = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Vendas[]>): HttpResponse<Vendas[]> {
        const jsonResponse: Vendas[] = res.body;
        const body: Vendas[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Vendas.
     */
    private convertItemFromServer(vendas: Vendas): Vendas {
        const copy: Vendas = Object.assign({}, vendas);
        return copy;
    }

    /**
     * Convert a Vendas to a JSON which can be sent to the server.
     */
    private convert(vendas: Vendas): Vendas {
        const copy: Vendas = Object.assign({}, vendas);
        return copy;
    }
}
