/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppDoraTestModule } from '../../../test.module';
import { CategoriaDetailComponent } from '../../../../../../main/webapp/app/entities/categoria/categoria-detail.component';
import { CategoriaService } from '../../../../../../main/webapp/app/entities/categoria/categoria.service';
import { Categoria } from '../../../../../../main/webapp/app/entities/categoria/categoria.model';

describe('Component Tests', () => {

    describe('Categoria Management Detail Component', () => {
        let comp: CategoriaDetailComponent;
        let fixture: ComponentFixture<CategoriaDetailComponent>;
        let service: CategoriaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [CategoriaDetailComponent],
                providers: [
                    CategoriaService
                ]
            })
            .overrideTemplate(CategoriaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategoriaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Categoria(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.categoria).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
