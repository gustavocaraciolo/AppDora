/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppDoraTestModule } from '../../../test.module';
import { NoticiaDetailComponent } from 'app/entities/noticia/noticia-detail.component';
import { Noticia } from 'app/shared/model/noticia.model';

describe('Component Tests', () => {
  describe('Noticia Management Detail Component', () => {
    let comp: NoticiaDetailComponent;
    let fixture: ComponentFixture<NoticiaDetailComponent>;
    const route = ({ data: of({ noticia: new Noticia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppDoraTestModule],
        declarations: [NoticiaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NoticiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NoticiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.noticia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
