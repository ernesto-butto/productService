import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AppList } from './app-list.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class AppListService {

    private resourceUrl = 'api/app-lists';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(appList: AppList): Observable<AppList> {
        let copy: AppList = Object.assign({}, appList);
        copy.registration = this.dateUtils
            .convertLocalDateToServer(appList.registration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(appList: AppList): Observable<AppList> {
        let copy: AppList = Object.assign({}, appList);
        copy.registration = this.dateUtils
            .convertLocalDateToServer(appList.registration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AppList> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.registration = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.registration);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].registration = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].registration);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
