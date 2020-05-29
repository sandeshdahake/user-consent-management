import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IValidation, Validation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';
import { ValidationComponent } from './validation.component';
import { ValidationDetailComponent } from './validation-detail.component';
import { ValidationUpdateComponent } from './validation-update.component';

@Injectable({ providedIn: 'root' })
export class ValidationResolve implements Resolve<IValidation> {
  constructor(private service: ValidationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IValidation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((validation: HttpResponse<Validation>) => {
          if (validation.body) {
            return of(validation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Validation());
  }
}

export const validationRoute: Routes = [
  {
    path: '',
    component: ValidationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Validations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ValidationDetailComponent,
    resolve: {
      validation: ValidationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Validations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ValidationUpdateComponent,
    resolve: {
      validation: ValidationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Validations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ValidationUpdateComponent,
    resolve: {
      validation: ValidationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Validations',
    },
    canActivate: [UserRouteAccessService],
  },
];
