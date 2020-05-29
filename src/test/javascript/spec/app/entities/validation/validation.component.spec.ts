import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConsentAppTestModule } from '../../../test.module';
import { ValidationComponent } from 'app/entities/validation/validation.component';
import { ValidationService } from 'app/entities/validation/validation.service';
import { Validation } from 'app/shared/model/validation.model';

describe('Component Tests', () => {
  describe('Validation Management Component', () => {
    let comp: ValidationComponent;
    let fixture: ComponentFixture<ValidationComponent>;
    let service: ValidationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [ValidationComponent],
      })
        .overrideTemplate(ValidationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValidationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Validation('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.validations && comp.validations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
