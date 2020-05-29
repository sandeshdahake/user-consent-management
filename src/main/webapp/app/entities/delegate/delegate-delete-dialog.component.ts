import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDelegate } from 'app/shared/model/delegate.model';
import { DelegateService } from './delegate.service';

@Component({
  templateUrl: './delegate-delete-dialog.component.html',
})
export class DelegateDeleteDialogComponent {
  delegate?: IDelegate;

  constructor(protected delegateService: DelegateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.delegateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('delegateListModification');
      this.activeModal.close();
    });
  }
}
