import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDelegate, Delegate } from 'app/shared/model/delegate.model';
import { DelegateService } from './delegate.service';

@Component({
  selector: 'jhi-delegate-update',
  templateUrl: './delegate-update.component.html',
})
export class DelegateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    delegateId: [],
    name: [],
  });

  constructor(protected delegateService: DelegateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegate }) => {
      this.updateForm(delegate);
    });
  }

  updateForm(delegate: IDelegate): void {
    this.editForm.patchValue({
      id: delegate.id,
      delegateId: delegate.delegateId,
      name: delegate.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const delegate = this.createFromForm();
    if (delegate.id !== undefined) {
      this.subscribeToSaveResponse(this.delegateService.update(delegate));
    } else {
      this.subscribeToSaveResponse(this.delegateService.create(delegate));
    }
  }

  private createFromForm(): IDelegate {
    return {
      ...new Delegate(),
      id: this.editForm.get(['id'])!.value,
      delegateId: this.editForm.get(['delegateId'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelegate>>): void {
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
