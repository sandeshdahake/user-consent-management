import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { PurposeUpdateComponent } from 'app/entities/purpose/purpose-update.component';
import { PurposeService } from 'app/entities/purpose/purpose.service';
import { Purpose } from 'app/shared/model/purpose.model';

describe('Component Tests', () => {
  describe('Purpose Management Update Component', () => {
    let comp: PurposeUpdateComponent;
    let fixture: ComponentFixture<PurposeUpdateComponent>;
    let service: PurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [PurposeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PurposeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurposeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurposeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Purpose('123');
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
        const entity = new Purpose();
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
