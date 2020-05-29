import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPreference, Preference } from 'app/shared/model/preference.model';
import { PreferenceService } from './preference.service';
import { IPurpose } from 'app/shared/model/purpose.model';
import { PurposeService } from 'app/entities/purpose/purpose.service';

@Component({
  selector: 'jhi-preference-update',
  templateUrl: './preference-update.component.html',
})
export class PreferenceUpdateComponent implements OnInit {
  isSaving = false;
  purposes: IPurpose[] = [];

  editForm = this.fb.group({
    id: [],
    preferenceId: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    purpose: [],
  });

  constructor(
    protected preferenceService: PreferenceService,
    protected purposeService: PurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preference }) => {
      this.updateForm(preference);

      this.purposeService.query().subscribe((res: HttpResponse<IPurpose[]>) => (this.purposes = res.body || []));
    });
  }

  updateForm(preference: IPreference): void {
    this.editForm.patchValue({
      id: preference.id,
      preferenceId: preference.preferenceId,
      enabled: preference.enabled,
      purpose: preference.purpose,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const preference = this.createFromForm();
    if (preference.id !== undefined) {
      this.subscribeToSaveResponse(this.preferenceService.update(preference));
    } else {
      this.subscribeToSaveResponse(this.preferenceService.create(preference));
    }
  }

  private createFromForm(): IPreference {
    return {
      ...new Preference(),
      id: this.editForm.get(['id'])!.value,
      preferenceId: this.editForm.get(['preferenceId'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreference>>): void {
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

  trackById(index: number, item: IPurpose): any {
    return item.id;
  }
}
