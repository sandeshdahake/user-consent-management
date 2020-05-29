import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsentAppSharedModule } from 'app/shared/shared.module';
import { ValidationComponent } from './validation.component';
import { ValidationDetailComponent } from './validation-detail.component';
import { ValidationUpdateComponent } from './validation-update.component';
import { ValidationDeleteDialogComponent } from './validation-delete-dialog.component';
import { validationRoute } from './validation.route';

@NgModule({
  imports: [ConsentAppSharedModule, RouterModule.forChild(validationRoute)],
  declarations: [ValidationComponent, ValidationDetailComponent, ValidationUpdateComponent, ValidationDeleteDialogComponent],
  entryComponents: [ValidationDeleteDialogComponent],
})
export class ConsentAppValidationModule {}
