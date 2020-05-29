import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDelegate } from 'app/shared/model/delegate.model';
import { DelegateService } from './delegate.service';
import { DelegateDeleteDialogComponent } from './delegate-delete-dialog.component';

@Component({
  selector: 'jhi-delegate',
  templateUrl: './delegate.component.html',
})
export class DelegateComponent implements OnInit, OnDestroy {
  delegates?: IDelegate[];
  eventSubscriber?: Subscription;

  constructor(protected delegateService: DelegateService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.delegateService.query().subscribe((res: HttpResponse<IDelegate[]>) => (this.delegates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDelegates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDelegate): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDelegates(): void {
    this.eventSubscriber = this.eventManager.subscribe('delegateListModification', () => this.loadAll());
  }

  delete(delegate: IDelegate): void {
    const modalRef = this.modalService.open(DelegateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.delegate = delegate;
  }
}
