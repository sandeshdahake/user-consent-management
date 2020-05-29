import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { PurposeDetailComponent } from 'app/entities/purpose/purpose-detail.component';
import { Purpose } from 'app/shared/model/purpose.model';

describe('Component Tests', () => {
  describe('Purpose Management Detail Component', () => {
    let comp: PurposeDetailComponent;
    let fixture: ComponentFixture<PurposeDetailComponent>;
    const route = ({ data: of({ purpose: new Purpose('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [PurposeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PurposeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurposeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load purpose on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.purpose).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
