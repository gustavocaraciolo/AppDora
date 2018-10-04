/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppDoraTestModule } from '../../../test.module';
import { UtilizadorDetailComponent } from 'app/entities/utilizador/utilizador-detail.component';
import { Utilizador } from 'app/shared/model/utilizador.model';

describe('Component Tests', () => {
  describe('Utilizador Management Detail Component', () => {
    let comp: UtilizadorDetailComponent;
    let fixture: ComponentFixture<UtilizadorDetailComponent>;
    const route = ({ data: of({ utilizador: new Utilizador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppDoraTestModule],
        declarations: [UtilizadorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UtilizadorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UtilizadorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.utilizador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
