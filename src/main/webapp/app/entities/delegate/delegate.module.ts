import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsentAppSharedModule } from 'app/shared/shared.module';
import { DelegateComponent } from './delegate.component';
import { DelegateDetailComponent } from './delegate-detail.component';
import { DelegateUpdateComponent } from './delegate-update.component';
import { DelegateDeleteDialogComponent } from './delegate-delete-dialog.component';
import { delegateRoute } from './delegate.route';

@NgModule({
  imports: [ConsentAppSharedModule, RouterModule.forChild(delegateRoute)],
  declarations: [DelegateComponent, DelegateDetailComponent, DelegateUpdateComponent, DelegateDeleteDialogComponent],
  entryComponents: [DelegateDeleteDialogComponent],
})
export class ConsentAppDelegateModule {}
