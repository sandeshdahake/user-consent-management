import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.ConsentAppClientModule),
      },
      {
        path: 'proof',
        loadChildren: () => import('./proof/proof.module').then(m => m.ConsentAppProofModule),
      },
      {
        path: 'delegate',
        loadChildren: () => import('./delegate/delegate.module').then(m => m.ConsentAppDelegateModule),
      },
      {
        path: 'validation',
        loadChildren: () => import('./validation/validation.module').then(m => m.ConsentAppValidationModule),
      },
      {
        path: 'preference',
        loadChildren: () => import('./preference/preference.module').then(m => m.ConsentAppPreferenceModule),
      },
      {
        path: 'purpose',
        loadChildren: () => import('./purpose/purpose.module').then(m => m.ConsentAppPurposeModule),
      },
      {
        path: 'consent',
        loadChildren: () => import('./consent/consent.module').then(m => m.ConsentAppConsentModule),
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.ConsentAppEventModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ConsentAppEntityModule {}
