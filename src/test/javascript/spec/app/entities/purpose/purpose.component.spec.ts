import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConsentAppTestModule } from '../../../test.module';
import { PurposeComponent } from 'app/entities/purpose/purpose.component';
import { PurposeService } from 'app/entities/purpose/purpose.service';
import { Purpose } from 'app/shared/model/purpose.model';

describe('Component Tests', () => {
  describe('Purpose Management Component', () => {
    let comp: PurposeComponent;
    let fixture: ComponentFixture<PurposeComponent>;
    let service: PurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [PurposeComponent],
      })
        .overrideTemplate(PurposeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurposeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurposeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Purpose('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.purposes && comp.purposes[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
