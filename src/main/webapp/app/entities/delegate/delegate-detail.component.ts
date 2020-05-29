import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelegate } from 'app/shared/model/delegate.model';

@Component({
  selector: 'jhi-delegate-detail',
  templateUrl: './delegate-detail.component.html',
})
export class DelegateDetailComponent implements OnInit {
  delegate: IDelegate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delegate }) => (this.delegate = delegate));
  }

  previousState(): void {
    window.history.back();
  }
}
