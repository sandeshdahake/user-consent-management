import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurpose } from 'app/shared/model/purpose.model';
import { PurposeService } from './purpose.service';

@Component({
  templateUrl: './purpose-delete-dialog.component.html',
})
export class PurposeDeleteDialogComponent {
  purpose?: IPurpose;

  constructor(protected purposeService: PurposeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.purposeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('purposeListModification');
      this.activeModal.close();
    });
  }
}
