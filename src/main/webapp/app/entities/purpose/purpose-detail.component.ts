import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurpose } from 'app/shared/model/purpose.model';

@Component({
  selector: 'jhi-purpose-detail',
  templateUrl: './purpose-detail.component.html',
})
export class PurposeDetailComponent implements OnInit {
  purpose: IPurpose | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purpose }) => (this.purpose = purpose));
  }

  previousState(): void {
    window.history.back();
  }
}
