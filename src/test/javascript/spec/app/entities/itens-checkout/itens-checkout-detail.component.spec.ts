/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppDoraTestModule } from '../../../test.module';
import { ItensCheckoutDetailComponent } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout-detail.component';
import { ItensCheckoutService } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.service';
import { ItensCheckout } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.model';

describe('Component Tests', () => {

    describe('ItensCheckout Management Detail Component', () => {
        let comp: ItensCheckoutDetailComponent;
        let fixture: ComponentFixture<ItensCheckoutDetailComponent>;
        let service: ItensCheckoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [ItensCheckoutDetailComponent],
                providers: [
                    ItensCheckoutService
                ]
            })
            .overrideTemplate(ItensCheckoutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItensCheckoutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItensCheckoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ItensCheckout(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.itensCheckout).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
