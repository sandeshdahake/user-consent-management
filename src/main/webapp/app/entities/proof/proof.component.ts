import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProof } from 'app/shared/model/proof.model';
import { ProofService } from './proof.service';
import { ProofDeleteDialogComponent } from './proof-delete-dialog.component';

@Component({
  selector: 'jhi-proof',
  templateUrl: './proof.component.html',
})
export class ProofComponent implements OnInit, OnDestroy {
  proofs?: IProof[];
  eventSubscriber?: Subscription;

  constructor(
    protected proofService: ProofService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.proofService.query().subscribe((res: HttpResponse<IProof[]>) => (this.proofs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProofs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProof): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInProofs(): void {
    this.eventSubscriber = this.eventManager.subscribe('proofListModification', () => this.loadAll());
  }

  delete(proof: IProof): void {
    const modalRef = this.modalService.open(ProofDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proof = proof;
  }
}
