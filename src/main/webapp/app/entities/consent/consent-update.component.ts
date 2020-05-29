import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConsent, Consent } from 'app/shared/model/consent.model';
import { ConsentService } from './consent.service';

@Component({
  selector: 'jhi-consent-update',
  templateUrl: './consent-update.component.html',
})
export class ConsentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected consentService: ConsentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consent }) => {
      this.updateForm(consent);
    });
  }

  updateForm(consent: IConsent): void {
    this.editForm.patchValue({
      id: consent.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consent = this.createFromForm();
    if (consent.id !== undefined) {
      this.subscribeToSaveResponse(this.consentService.update(consent));
    } else {
      this.subscribeToSaveResponse(this.consentService.create(consent));
    }
  }

  private createFromForm(): IConsent {
    return {
      ...new Consent(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsent>>): void {
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
}
