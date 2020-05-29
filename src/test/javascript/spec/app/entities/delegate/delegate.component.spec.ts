import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConsentAppTestModule } from '../../../test.module';
import { DelegateComponent } from 'app/entities/delegate/delegate.component';
import { DelegateService } from 'app/entities/delegate/delegate.service';
import { Delegate } from 'app/shared/model/delegate.model';

describe('Component Tests', () => {
  describe('Delegate Management Component', () => {
    let comp: DelegateComponent;
    let fixture: ComponentFixture<DelegateComponent>;
    let service: DelegateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [DelegateComponent],
      })
        .overrideTemplate(DelegateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Delegate('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.delegates && comp.delegates[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
