import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsentAppSharedModule } from 'app/shared/shared.module';
import { PurposeComponent } from './purpose.component';
import { PurposeDetailComponent } from './purpose-detail.component';
import { PurposeUpdateComponent } from './purpose-update.component';
import { PurposeDeleteDialogComponent } from './purpose-delete-dialog.component';
import { purposeRoute } from './purpose.route';

@NgModule({
  imports: [ConsentAppSharedModule, RouterModule.forChild(purposeRoute)],
  declarations: [PurposeComponent, PurposeDetailComponent, PurposeUpdateComponent, PurposeDeleteDialogComponent],
  entryComponents: [PurposeDeleteDialogComponent],
})
export class ConsentAppPurposeModule {}
