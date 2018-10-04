/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppDoraTestModule } from '../../../test.module';
import { NoticiaDeleteDialogComponent } from 'app/entities/noticia/noticia-delete-dialog.component';
import { NoticiaService } from 'app/entities/noticia/noticia.service';

describe('Component Tests', () => {
  describe('Noticia Management Delete Component', () => {
    let comp: NoticiaDeleteDialogComponent;
    let fixture: ComponentFixture<NoticiaDeleteDialogComponent>;
    let service: NoticiaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppDoraTestModule],
        declarations: [NoticiaDeleteDialogComponent]
      })
        .overrideTemplate(NoticiaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NoticiaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NoticiaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
