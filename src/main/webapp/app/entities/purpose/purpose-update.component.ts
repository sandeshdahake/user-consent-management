import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPurpose, Purpose } from 'app/shared/model/purpose.model';
import { PurposeService } from './purpose.service';
import { IConsent } from 'app/shared/model/consent.model';
import { ConsentService } from 'app/entities/consent/consent.service';

@Component({
  selector: 'jhi-purpose-update',
  templateUrl: './purpose-update.component.html',
})
export class PurposeUpdateComponent implements OnInit {
  isSaving = false;
  consents: IConsent[] = [];

  editForm = this.fb.group({
    id: [],
    purposeId: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    consent: [],
  });

  constructor(
    protected purposeService: PurposeService,
    protected consentService: ConsentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purpose }) => {
      this.updateForm(purpose);

      this.consentService.query().subscribe((res: HttpResponse<IConsent[]>) => (this.consents = res.body || []));
    });
  }

  updateForm(purpose: IPurpose): void {
    this.editForm.patchValue({
      id: purpose.id,
      purposeId: purpose.purposeId,
      enabled: purpose.enabled,
      consent: purpose.consent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purpose = this.createFromForm();
    if (purpose.id !== undefined) {
      this.subscribeToSaveResponse(this.purposeService.update(purpose));
    } else {
      this.subscribeToSaveResponse(this.purposeService.create(purpose));
    }
  }

  private createFromForm(): IPurpose {
    return {
      ...new Purpose(),
      id: this.editForm.get(['id'])!.value,
      purposeId: this.editForm.get(['purposeId'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      consent: this.editForm.get(['consent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurpose>>): void {
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

  trackById(index: number, item: IConsent): any {
    return item.id;
  }
}
