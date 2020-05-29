import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDelegate } from 'app/shared/model/delegate.model';

type EntityResponseType = HttpResponse<IDelegate>;
type EntityArrayResponseType = HttpResponse<IDelegate[]>;

@Injectable({ providedIn: 'root' })
export class DelegateService {
  public resourceUrl = SERVER_API_URL + 'api/delegates';

  constructor(protected http: HttpClient) {}

  create(delegate: IDelegate): Observable<EntityResponseType> {
    return this.http.post<IDelegate>(this.resourceUrl, delegate, { observe: 'response' });
  }

  update(delegate: IDelegate): Observable<EntityResponseType> {
    return this.http.put<IDelegate>(this.resourceUrl, delegate, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDelegate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDelegate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
