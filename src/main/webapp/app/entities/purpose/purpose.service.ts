import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurpose } from 'app/shared/model/purpose.model';

type EntityResponseType = HttpResponse<IPurpose>;
type EntityArrayResponseType = HttpResponse<IPurpose[]>;

@Injectable({ providedIn: 'root' })
export class PurposeService {
  public resourceUrl = SERVER_API_URL + 'api/purposes';

  constructor(protected http: HttpClient) {}

  create(purpose: IPurpose): Observable<EntityResponseType> {
    return this.http.post<IPurpose>(this.resourceUrl, purpose, { observe: 'response' });
  }

  update(purpose: IPurpose): Observable<EntityResponseType> {
    return this.http.put<IPurpose>(this.resourceUrl, purpose, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPurpose>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPurpose[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
