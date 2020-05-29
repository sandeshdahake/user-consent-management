import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPurpose, Purpose } from 'app/shared/model/purpose.model';
import { PurposeService } from './purpose.service';
import { PurposeComponent } from './purpose.component';
import { PurposeDetailComponent } from './purpose-detail.component';
import { PurposeUpdateComponent } from './purpose-update.component';

@Injectable({ providedIn: 'root' })
export class PurposeResolve implements Resolve<IPurpose> {
  constructor(private service: PurposeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurpose> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((purpose: HttpResponse<Purpose>) => {
          if (purpose.body) {
            return of(purpose.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Purpose());
  }
}

export const purposeRoute: Routes = [
  {
    path: '',
    component: PurposeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Purposes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PurposeDetailComponent,
    resolve: {
      purpose: PurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Purposes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PurposeUpdateComponent,
    resolve: {
      purpose: PurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Purposes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PurposeUpdateComponent,
    resolve: {
      purpose: PurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Purposes',
    },
    canActivate: [UserRouteAccessService],
  },
];
