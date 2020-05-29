import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsent } from 'app/shared/model/consent.model';
import { ConsentService } from './consent.service';

@Component({
  templateUrl: './consent-delete-dialog.component.html',
})
export class ConsentDeleteDialogComponent {
  consent?: IConsent;

  constructor(protected consentService: ConsentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.consentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('consentListModification');
      this.activeModal.close();
    });
  }
}
