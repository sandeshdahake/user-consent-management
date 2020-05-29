import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDelegate, Delegate } from 'app/shared/model/delegate.model';
import { DelegateService } from './delegate.service';
import { DelegateComponent } from './delegate.component';
import { DelegateDetailComponent } from './delegate-detail.component';
import { DelegateUpdateComponent } from './delegate-update.component';

@Injectable({ providedIn: 'root' })
export class DelegateResolve implements Resolve<IDelegate> {
  constructor(private service: DelegateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDelegate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((delegate: HttpResponse<Delegate>) => {
          if (delegate.body) {
            return of(delegate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Delegate());
  }
}

export const delegateRoute: Routes = [
  {
    path: '',
    component: DelegateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Delegates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DelegateDetailComponent,
    resolve: {
      delegate: DelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Delegates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DelegateUpdateComponent,
    resolve: {
      delegate: DelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Delegates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DelegateUpdateComponent,
    resolve: {
      delegate: DelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Delegates',
    },
    canActivate: [UserRouteAccessService],
  },
];
