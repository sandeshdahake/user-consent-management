import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPurpose } from 'app/shared/model/purpose.model';
import { PurposeService } from './purpose.service';
import { PurposeDeleteDialogComponent } from './purpose-delete-dialog.component';

@Component({
  selector: 'jhi-purpose',
  templateUrl: './purpose.component.html',
})
export class PurposeComponent implements OnInit, OnDestroy {
  purposes?: IPurpose[];
  eventSubscriber?: Subscription;

  constructor(protected purposeService: PurposeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.purposeService.query().subscribe((res: HttpResponse<IPurpose[]>) => (this.purposes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPurposes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPurpose): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPurposes(): void {
    this.eventSubscriber = this.eventManager.subscribe('purposeListModification', () => this.loadAll());
  }

  delete(purpose: IPurpose): void {
    const modalRef = this.modalService.open(PurposeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.purpose = purpose;
  }
}
