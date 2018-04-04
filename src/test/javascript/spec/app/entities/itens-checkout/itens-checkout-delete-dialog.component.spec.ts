/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AppDoraTestModule } from '../../../test.module';
import { ItensCheckoutDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout-delete-dialog.component';
import { ItensCheckoutService } from '../../../../../../main/webapp/app/entities/itens-checkout/itens-checkout.service';

describe('Component Tests', () => {

    describe('ItensCheckout Management Delete Component', () => {
        let comp: ItensCheckoutDeleteDialogComponent;
        let fixture: ComponentFixture<ItensCheckoutDeleteDialogComponent>;
        let service: ItensCheckoutService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppDoraTestModule],
                declarations: [ItensCheckoutDeleteDialogComponent],
                providers: [
                    ItensCheckoutService
                ]
            })
            .overrideTemplate(ItensCheckoutDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItensCheckoutDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItensCheckoutService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
