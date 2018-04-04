/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AppDoraTestModule } from '../../../test.module';
import { CheckoutDialogComponent } from '../../../../../../main/webapp/app/entities/checkout/checkout-dialog.component';
import { CheckoutService } from '../../../../../../main/webapp/app/entities/checkout/checkout.service';
import { Checkout } from '../../../../../../main/webapp/app/entities/checkout/checkout.model';
import { ClienteService } from '../../../../../../main/webapp/app/entities/cliente';
import { ItensCheckoutService } from '../../../../../../main/webapp/app/entities/itens-checkout';

describe('Component Tests', () => {

    describe('Checkout Management Dialog Component', () => {
        let comp: CheckoutDialogComponent;
        let fixture: ComponentFixture<CheckoutDialogComponent>;
        let service: CheckoutService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [CheckoutDialogComponent],
                providers: [
                    ClienteService,
                    ItensCheckoutService,
                    CheckoutService
                ]
            })
            .overrideTemplate(CheckoutDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CheckoutDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CheckoutService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Checkout(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.checkout = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'checkoutListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Checkout();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.checkout = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'checkoutListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
