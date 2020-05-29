import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IConsent } from 'app/shared/model/consent.model';
import { ConsentService } from 'app/entities/consent/consent.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  consents: IConsent[] = [];

  editForm = this.fb.group({
    id: [],
    organizationUserId: [null, [Validators.required]],
    version: [],
    createdAt: [],
    updatedAt: [],
    customerId: [null, [Validators.required]],
    country: [null, [Validators.required]],
    consents: [],
  });

  constructor(
    protected clientService: ClientService,
    protected consentService: ConsentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      if (!client.id) {
        const today = moment().startOf('day');
        client.createdAt = today;
        client.updatedAt = today;
      }

      this.updateForm(client);

      this.consentService
        .query({ filter: 'client-is-null' })
        .pipe(
          map((res: HttpResponse<IConsent[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IConsent[]) => {
          if (!client.consents || !client.consents.id) {
            this.consents = resBody;
          } else {
            this.consentService
              .find(client.consents.id)
              .pipe(
                map((subRes: HttpResponse<IConsent>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IConsent[]) => (this.consents = concatRes));
          }
        });
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      organizationUserId: client.organizationUserId,
      version: client.version,
      createdAt: client.createdAt ? client.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: client.updatedAt ? client.updatedAt.format(DATE_TIME_FORMAT) : null,
      customerId: client.customerId,
      country: client.country,
      consents: client.consents,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      organizationUserId: this.editForm.get(['organizationUserId'])!.value,
      version: this.editForm.get(['version'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      customerId: this.editForm.get(['customerId'])!.value,
      country: this.editForm.get(['country'])!.value,
      consents: this.editForm.get(['consents'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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
