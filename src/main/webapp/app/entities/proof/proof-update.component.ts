import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProof, Proof } from 'app/shared/model/proof.model';
import { ProofService } from './proof.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

@Component({
  selector: 'jhi-proof-update',
  templateUrl: './proof-update.component.html',
})
export class ProofUpdateComponent implements OnInit {
  isSaving = false;
  events: IEvent[] = [];

  editForm = this.fb.group({
    id: [],
    filename: [],
    file: [null, []],
    fileContentType: [],
    event: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected proofService: ProofService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proof }) => {
      this.updateForm(proof);

      this.eventService.query().subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body || []));
    });
  }

  updateForm(proof: IProof): void {
    this.editForm.patchValue({
      id: proof.id,
      filename: proof.filename,
      file: proof.file,
      fileContentType: proof.fileContentType,
      event: proof.event,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('consentApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proof = this.createFromForm();
    if (proof.id !== undefined) {
      this.subscribeToSaveResponse(this.proofService.update(proof));
    } else {
      this.subscribeToSaveResponse(this.proofService.create(proof));
    }
  }

  private createFromForm(): IProof {
    return {
      ...new Proof(),
      id: this.editForm.get(['id'])!.value,
      filename: this.editForm.get(['filename'])!.value,
      fileContentType: this.editForm.get(['fileContentType'])!.value,
      file: this.editForm.get(['file'])!.value,
      event: this.editForm.get(['event'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProof>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEvent): any {
    return item.id;
  }
}
