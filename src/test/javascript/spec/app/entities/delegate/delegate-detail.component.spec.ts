import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { DelegateDetailComponent } from 'app/entities/delegate/delegate-detail.component';
import { Delegate } from 'app/shared/model/delegate.model';

describe('Component Tests', () => {
  describe('Delegate Management Detail Component', () => {
    let comp: DelegateDetailComponent;
    let fixture: ComponentFixture<DelegateDetailComponent>;
    const route = ({ data: of({ delegate: new Delegate('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [DelegateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DelegateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DelegateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load delegate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.delegate).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
