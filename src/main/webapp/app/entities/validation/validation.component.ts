import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IValidation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';
import { ValidationDeleteDialogComponent } from './validation-delete-dialog.component';

@Component({
  selector: 'jhi-validation',
  templateUrl: './validation.component.html',
})
export class ValidationComponent implements OnInit, OnDestroy {
  validations?: IValidation[];
  eventSubscriber?: Subscription;

  constructor(protected validationService: ValidationService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.validationService.query().subscribe((res: HttpResponse<IValidation[]>) => (this.validations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInValidations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IValidation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInValidations(): void {
    this.eventSubscriber = this.eventManager.subscribe('validationListModification', () => this.loadAll());
  }

  delete(validation: IValidation): void {
    const modalRef = this.modalService.open(ValidationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.validation = validation;
  }
}
