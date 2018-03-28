/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppDoraTestModule } from '../../../test.module';
import { CheckoutComponent } from '../../../../../../main/webapp/app/entities/checkout/checkout.component';
import { CheckoutService } from '../../../../../../main/webapp/app/entities/checkout/checkout.service';
import { Checkout } from '../../../../../../main/webapp/app/entities/checkout/checkout.model';

describe('Component Tests', () => {

    describe('Checkout Management Component', () => {
        let comp: CheckoutComponent;
        let fixture: ComponentFixture<CheckoutComponent>;
        let service: CheckoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [CheckoutComponent],
                providers: [
                    CheckoutService
                ]
            })
            .overrideTemplate(CheckoutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CheckoutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Checkout(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.checkouts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
