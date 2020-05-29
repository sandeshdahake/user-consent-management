import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { ValidationDetailComponent } from 'app/entities/validation/validation-detail.component';
import { Validation } from 'app/shared/model/validation.model';

describe('Component Tests', () => {
  describe('Validation Management Detail Component', () => {
    let comp: ValidationDetailComponent;
    let fixture: ComponentFixture<ValidationDetailComponent>;
    const route = ({ data: of({ validation: new Validation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [ValidationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ValidationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValidationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load validation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.validation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
