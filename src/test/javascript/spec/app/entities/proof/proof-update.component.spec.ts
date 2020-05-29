import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { ProofUpdateComponent } from 'app/entities/proof/proof-update.component';
import { ProofService } from 'app/entities/proof/proof.service';
import { Proof } from 'app/shared/model/proof.model';

describe('Component Tests', () => {
  describe('Proof Management Update Component', () => {
    let comp: ProofUpdateComponent;
    let fixture: ComponentFixture<ProofUpdateComponent>;
    let service: ProofService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [ProofUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProofUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProofUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProofService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proof('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proof();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
