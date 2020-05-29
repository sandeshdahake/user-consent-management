import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValidation } from 'app/shared/model/validation.model';

@Component({
  selector: 'jhi-validation-detail',
  templateUrl: './validation-detail.component.html',
})
export class ValidationDetailComponent implements OnInit {
  validation: IValidation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validation }) => (this.validation = validation));
  }

  previousState(): void {
    window.history.back();
  }
}
