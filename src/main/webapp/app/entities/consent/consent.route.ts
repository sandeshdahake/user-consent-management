import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConsent, Consent } from 'app/shared/model/consent.model';
import { ConsentService } from './consent.service';
import { ConsentComponent } from './consent.component';
import { ConsentDetailComponent } from './consent-detail.component';
import { ConsentUpdateComponent } from './consent-update.component';

@Injectable({ providedIn: 'root' })
export class ConsentResolve implements Resolve<IConsent> {
  constructor(private service: ConsentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((consent: HttpResponse<Consent>) => {
          if (consent.body) {
            return of(consent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Consent());
  }
}

export const consentRoute: Routes = [
  {
    path: '',
    component: ConsentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Consents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsentDetailComponent,
    resolve: {
      consent: ConsentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Consents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsentUpdateComponent,
    resolve: {
      consent: ConsentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Consents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsentUpdateComponent,
    resolve: {
      consent: ConsentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Consents',
    },
    canActivate: [UserRouteAccessService],
  },
];
