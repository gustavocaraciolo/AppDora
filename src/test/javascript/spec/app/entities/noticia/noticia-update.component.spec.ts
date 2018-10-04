/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppDoraTestModule } from '../../../test.module';
import { NoticiaUpdateComponent } from 'app/entities/noticia/noticia-update.component';
import { NoticiaService } from 'app/entities/noticia/noticia.service';
import { Noticia } from 'app/shared/model/noticia.model';

describe('Component Tests', () => {
  describe('Noticia Management Update Component', () => {
    let comp: NoticiaUpdateComponent;
    let fixture: ComponentFixture<NoticiaUpdateComponent>;
    let service: NoticiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppDoraTestModule],
        declarations: [NoticiaUpdateComponent]
      })
        .overrideTemplate(NoticiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NoticiaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NoticiaService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Noticia(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.noticia = entity;
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
          const entity = new Noticia();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.noticia = entity;
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
