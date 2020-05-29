import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IValidation, Validation } from 'app/shared/model/validation.model';
import { ValidationService } from './validation.service';

@Component({
  selector: 'jhi-validation-update',
  templateUrl: './validation-update.component.html',
})
export class ValidationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
  });

  constructor(protected validationService: ValidationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validation }) => {
      this.updateForm(validation);
    });
  }

  updateForm(validation: IValidation): void {
    this.editForm.patchValue({
      id: validation.id,
      type: validation.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const validation = this.createFromForm();
    if (validation.id !== undefined) {
      this.subscribeToSaveResponse(this.validationService.update(validation));
    } else {
      this.subscribeToSaveResponse(this.validationService.create(validation));
    }
  }

  private createFromForm(): IValidation {
    return {
      ...new Validation(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValidation>>): void {
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
