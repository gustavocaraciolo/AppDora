/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AppDoraTestModule } from '../../../test.module';
import { ItensCheckoutDialogComponent } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout-dialog.component';
import { ItensCheckoutService } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.service';
import { ItensCheckout } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.model';
import { ProdutoService } from '../../../../../../main/webapp/app/entities/produto';
import { CheckoutService } from '../../../../../../main/webapp/app/entities/checkout';

describe('Component Tests', () => {

    describe('ItensCheckout Management Dialog Component', () => {
        let comp: ItensCheckoutDialogComponent;
        let fixture: ComponentFixture<ItensCheckoutDialogComponent>;
        let service: ItensCheckoutService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [ItensCheckoutDialogComponent],
                providers: [
                    ProdutoService,
                    CheckoutService,
                    ItensCheckoutService
                ]
            })
            .overrideTemplate(ItensCheckoutDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItensCheckoutDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItensCheckoutService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ItensCheckout(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.itensCheckout = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'itensCheckoutListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ItensCheckout();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.itensCheckout = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'itensCheckoutListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
