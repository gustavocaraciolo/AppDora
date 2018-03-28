/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppDoraTestModule } from '../../../test.module';
import { CheckoutDetailComponent } from '../../../../../../main/webapp/app/entities/checkout/checkout-detail.component';
import { CheckoutService } from '../../../../../../main/webapp/app/entities/checkout/checkout.service';
import { Checkout } from '../../../../../../main/webapp/app/entities/checkout/checkout.model';

describe('Component Tests', () => {

    describe('Checkout Management Detail Component', () => {
        let comp: CheckoutDetailComponent;
        let fixture: ComponentFixture<CheckoutDetailComponent>;
        let service: CheckoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [CheckoutDetailComponent],
                providers: [
                    CheckoutService
                ]
            })
            .overrideTemplate(CheckoutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CheckoutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Checkout(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.checkout).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
