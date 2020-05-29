import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IValidation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';

@Component({
  templateUrl: './validation-delete-dialog.component.html',
})
export class ValidationDeleteDialogComponent {
  validation?: IValidation;

  constructor(
    protected validationService: ValidationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.validationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('validationListModification');
      this.activeModal.close();
    });
  }
}
