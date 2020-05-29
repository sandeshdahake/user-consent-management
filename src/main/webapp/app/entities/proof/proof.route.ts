import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProof, Proof } from 'app/shared/model/proof.model';
import { ProofService } from './proof.service';
import { ProofComponent } from './proof.component';
import { ProofDetailComponent } from './proof-detail.component';
import { ProofUpdateComponent } from './proof-update.component';

@Injectable({ providedIn: 'root' })
export class ProofResolve implements Resolve<IProof> {
  constructor(private service: ProofService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProof> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proof: HttpResponse<Proof>) => {
          if (proof.body) {
            return of(proof.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Proof());
  }
}

export const proofRoute: Routes = [
  {
    path: '',
    component: ProofComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Proofs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProofDetailComponent,
    resolve: {
      proof: ProofResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Proofs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProofUpdateComponent,
    resolve: {
      proof: ProofResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Proofs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProofUpdateComponent,
    resolve: {
      proof: ProofResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Proofs',
    },
    canActivate: [UserRouteAccessService],
  },
];
