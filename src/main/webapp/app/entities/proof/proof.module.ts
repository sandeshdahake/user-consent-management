import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsentAppSharedModule } from 'app/shared/shared.module';
import { ProofComponent } from './proof.component';
import { ProofDetailComponent } from './proof-detail.component';
import { ProofUpdateComponent } from './proof-update.component';
import { ProofDeleteDialogComponent } from './proof-delete-dialog.component';
import { proofRoute } from './proof.route';

@NgModule({
  imports: [ConsentAppSharedModule, RouterModule.forChild(proofRoute)],
  declarations: [ProofComponent, ProofDetailComponent, ProofUpdateComponent, ProofDeleteDialogComponent],
  entryComponents: [ProofDeleteDialogComponent],
})
export class ConsentAppProofModule {}
