import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Checkout } from './checkout.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Checkout>;

@Injectable()
export class CheckoutService {

    private resourceUrl =  SERVER_API_URL + 'api/checkouts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/checkouts';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(checkout: Checkout): Observable<EntityResponseType> {
        const copy = this.convert(checkout);
        return this.http.post<Checkout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(checkout: Checkout): Observable<EntityResponseType> {
        const copy = this.convert(checkout);
        return this.http.put<Checkout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Checkout>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Checkout[]>> {
        const options = createRequestOption(req);
        return this.http.get<Checkout[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Checkout[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Checkout[]>> {
        const options = createRequestOption(req);
        return this.http.get<Checkout[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Checkout[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Checkout = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Checkout[]>): HttpResponse<Checkout[]> {
        const jsonResponse: Checkout[] = res.body;
        const body: Checkout[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Checkout.
     */
    private convertItemFromServer(checkout: Checkout): Checkout {
        const copy: Checkout = Object.assign({}, checkout);
        copy.dataHora = this.dateUtils
            .convertDateTimeFromServer(checkout.dataHora);
        return copy;
    }

    /**
     * Convert a Checkout to a JSON which can be sent to the server.
     */
    private convert(checkout: Checkout): Checkout {
        const copy: Checkout = Object.assign({}, checkout);

        //copy.dataHora = this.dateUtils.toDate(checkout.dataHora);
        return copy;
    }
}
