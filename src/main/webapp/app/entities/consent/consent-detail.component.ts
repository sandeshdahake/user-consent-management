import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsent } from 'app/shared/model/consent.model';

@Component({
  selector: 'jhi-consent-detail',
  templateUrl: './consent-detail.component.html',
})
export class ConsentDetailComponent implements OnInit {
  consent: IConsent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consent }) => (this.consent = consent));
  }

  previousState(): void {
    window.history.back();
  }
}
