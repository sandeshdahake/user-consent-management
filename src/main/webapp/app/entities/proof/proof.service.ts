import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProof } from 'app/shared/model/proof.model';

type EntityResponseType = HttpResponse<IProof>;
type EntityArrayResponseType = HttpResponse<IProof[]>;

@Injectable({ providedIn: 'root' })
export class ProofService {
  public resourceUrl = SERVER_API_URL + 'api/proofs';

  constructor(protected http: HttpClient) {}

  create(proof: IProof): Observable<EntityResponseType> {
    return this.http.post<IProof>(this.resourceUrl, proof, { observe: 'response' });
  }

  update(proof: IProof): Observable<EntityResponseType> {
    return this.http.put<IProof>(this.resourceUrl, proof, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProof>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProof[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
