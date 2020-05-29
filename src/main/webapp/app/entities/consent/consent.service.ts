import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsent } from 'app/shared/model/consent.model';

type EntityResponseType = HttpResponse<IConsent>;
type EntityArrayResponseType = HttpResponse<IConsent[]>;

@Injectable({ providedIn: 'root' })
export class ConsentService {
  public resourceUrl = SERVER_API_URL + 'api/consents';

  constructor(protected http: HttpClient) {}

  create(consent: IConsent): Observable<EntityResponseType> {
    return this.http.post<IConsent>(this.resourceUrl, consent, { observe: 'response' });
  }

  update(consent: IConsent): Observable<EntityResponseType> {
    return this.http.put<IConsent>(this.resourceUrl, consent, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IConsent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConsent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
