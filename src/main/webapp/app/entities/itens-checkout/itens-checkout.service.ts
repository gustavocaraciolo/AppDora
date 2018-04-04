import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ItensCheckout } from './itens-checkout.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ItensCheckout>;

@Injectable()
export class ItensCheckoutService {

    private resourceUrl =  SERVER_API_URL + 'api/itens-checkouts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/itens-checkouts';

    constructor(private http: HttpClient) { }

    create(itensCheckout: ItensCheckout): Observable<EntityResponseType> {
        const copy = this.convert(itensCheckout);
        return this.http.post<ItensCheckout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(itensCheckout: ItensCheckout): Observable<EntityResponseType> {
        const copy = this.convert(itensCheckout);
        return this.http.put<ItensCheckout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ItensCheckout>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ItensCheckout[]>> {
        const options = createRequestOption(req);
        return this.http.get<ItensCheckout[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ItensCheckout[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ItensCheckout[]>> {
        const options = createRequestOption(req);
        return this.http.get<ItensCheckout[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ItensCheckout[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ItensCheckout = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ItensCheckout[]>): HttpResponse<ItensCheckout[]> {
        const jsonResponse: ItensCheckout[] = res.body;
        const body: ItensCheckout[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ItensCheckout.
     */
    private convertItemFromServer(itensCheckout: ItensCheckout): ItensCheckout {
        const copy: ItensCheckout = Object.assign({}, itensCheckout);
        return copy;
    }

    /**
     * Convert a ItensCheckout to a JSON which can be sent to the server.
     */
    private convert(itensCheckout: ItensCheckout): ItensCheckout {
        const copy: ItensCheckout = Object.assign({}, itensCheckout);
        return copy;
    }
}
