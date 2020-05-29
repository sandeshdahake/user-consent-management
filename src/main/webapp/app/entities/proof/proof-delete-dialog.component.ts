import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProof } from 'app/shared/model/proof.model';
import { ProofService } from './proof.service';

@Component({
  templateUrl: './proof-delete-dialog.component.html',
})
export class ProofDeleteDialogComponent {
  proof?: IProof;

  constructor(protected proofService: ProofService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.proofService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proofListModification');
      this.activeModal.close();
    });
  }
}
