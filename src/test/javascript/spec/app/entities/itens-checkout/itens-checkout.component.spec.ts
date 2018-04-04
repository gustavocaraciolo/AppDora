/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppDoraTestModule } from '../../../test.module';
import { ItensCheckoutComponent } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.component';
import { ItensCheckoutService } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.service';
import { ItensCheckout } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.model';

describe('Component Tests', () => {

    describe('ItensCheckout Management Component', () => {
        let comp: ItensCheckoutComponent;
        let fixture: ComponentFixture<ItensCheckoutComponent>;
        let service: ItensCheckoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [ItensCheckoutComponent],
                providers: [
                    ItensCheckoutService
                ]
            })
            .overrideTemplate(ItensCheckoutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItensCheckoutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItensCheckoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ItensCheckout(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.itensCheckouts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
