import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConsentAppTestModule } from '../../../test.module';
import { DelegateUpdateComponent } from 'app/entities/delegate/delegate-update.component';
import { DelegateService } from 'app/entities/delegate/delegate.service';
import { Delegate } from 'app/shared/model/delegate.model';

describe('Component Tests', () => {
  describe('Delegate Management Update Component', () => {
    let comp: DelegateUpdateComponent;
    let fixture: ComponentFixture<DelegateUpdateComponent>;
    let service: DelegateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConsentAppTestModule],
        declarations: [DelegateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DelegateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Delegate('123');
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
        const entity = new Delegate();
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
