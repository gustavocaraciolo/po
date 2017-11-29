import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { RequestItems } from './request-items.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RequestItemsService {

    private resourceUrl = SERVER_API_URL + 'api/request-items';

    constructor(private http: Http) { }

    create(requestItems: RequestItems): Observable<RequestItems> {
        const copy = this.convert(requestItems);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(requestItems: RequestItems): Observable<RequestItems> {
        const copy = this.convert(requestItems);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RequestItems> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to RequestItems.
     */
    private convertItemFromServer(json: any): RequestItems {
        const entity: RequestItems = Object.assign(new RequestItems(), json);
        return entity;
    }

    /**
     * Convert a RequestItems to a JSON which can be sent to the server.
     */
    private convert(requestItems: RequestItems): RequestItems {
        const copy: RequestItems = Object.assign({}, requestItems);
        return copy;
    }
}
