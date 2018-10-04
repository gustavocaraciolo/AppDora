/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppDoraTestModule } from '../../../test.module';
import { UtilizadorUpdateComponent } from 'app/entities/utilizador/utilizador-update.component';
import { UtilizadorService } from 'app/entities/utilizador/utilizador.service';
import { Utilizador } from 'app/shared/model/utilizador.model';

describe('Component Tests', () => {
  describe('Utilizador Management Update Component', () => {
    let comp: UtilizadorUpdateComponent;
    let fixture: ComponentFixture<UtilizadorUpdateComponent>;
    let service: UtilizadorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppDoraTestModule],
        declarations: [UtilizadorUpdateComponent]
      })
        .overrideTemplate(UtilizadorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UtilizadorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UtilizadorService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Utilizador(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.utilizador = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Utilizador();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.utilizador = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
