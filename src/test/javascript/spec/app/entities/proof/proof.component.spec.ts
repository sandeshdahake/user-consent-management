import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConsentAppTestModule } from '../../../test.module';
import { ProofComponent } from 'app/entities/proof/proof.component';
import { ProofService } from 'app/entities/proof/proof.service';
import { Proof } from 'app/shared/model/proof.model';

describe('Component Tests', () => {
  describe('Proof Management Component', () => {
    let comp: ProofComponent;
    let fixture: ComponentFixture<ProofComponent>;
    let service: ProofService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [ProofComponent],
      })
        .overrideTemplate(ProofComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProofComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProofService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Proof('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proofs && comp.proofs[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
