import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IDelegate } from 'app/shared/model/delegate.model';
import { DelegateService } from 'app/entities/delegate/delegate.service';
import { IValidation } from 'app/shared/model/validation.model';
import { ValidationService } from 'app/entities/validation/validation.service';
import { IConsent } from 'app/shared/model/consent.model';
import { ConsentService } from 'app/entities/consent/consent.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

type SelectableEntity = IDelegate | IValidation | IConsent | IClient;

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html',
})
export class EventUpdateComponent implements OnInit {
  isSaving = false;
  delegates: IDelegate[] = [];
  validations: IValidation[] = [];
  consents: IConsent[] = [];
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    organizationId: [null, [Validators.required]],
    createdAt: [],
    status: [null, [Validators.required]],
    delegate: [],
    validation: [],
    consents: [],
    user: [],
  });

  constructor(
    protected eventService: EventService,
    protected delegateService: DelegateService,
    protected validationService: ValidationService,
    protected consentService: ConsentService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      if (!event.id) {
        const today = moment().startOf('day');
        event.createdAt = today;
      }

      this.updateForm(event);

      this.delegateService
        .query({ filter: 'event-is-null' })
        .pipe(
          map((res: HttpResponse<IDelegate[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDelegate[]) => {
          if (!event.delegate || !event.delegate.id) {
            this.delegates = resBody;
          } else {
            this.delegateService
              .find(event.delegate.id)
              .pipe(
                map((subRes: HttpResponse<IDelegate>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDelegate[]) => (this.delegates = concatRes));
          }
        });

      this.validationService
        .query({ filter: 'event-is-null' })
        .pipe(
          map((res: HttpResponse<IValidation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IValidation[]) => {
          if (!event.validation || !event.validation.id) {
            this.validations = resBody;
          } else {
            this.validationService
              .find(event.validation.id)
              .pipe(
                map((subRes: HttpResponse<IValidation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IValidation[]) => (this.validations = concatRes));
          }
        });

      this.consentService
        .query({ filter: 'event-is-null' })
        .pipe(
          map((res: HttpResponse<IConsent[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IConsent[]) => {
          if (!event.consents || !event.consents.id) {
            this.consents = resBody;
          } else {
            this.consentService
              .find(event.consents.id)
              .pipe(
                map((subRes: HttpResponse<IConsent>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IConsent[]) => (this.consents = concatRes));
          }
        });

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(event: IEvent): void {
    this.editForm.patchValue({
      id: event.id,
      organizationId: event.organizationId,
      createdAt: event.createdAt ? event.createdAt.format(DATE_TIME_FORMAT) : null,
      status: event.status,
      delegate: event.delegate,
      validation: event.validation,
      consents: event.consents,
      user: event.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id'])!.value,
      organizationId: this.editForm.get(['organizationId'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      delegate: this.editForm.get(['delegate'])!.value,
      validation: this.editForm.get(['validation'])!.value,
      consents: this.editForm.get(['consents'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
