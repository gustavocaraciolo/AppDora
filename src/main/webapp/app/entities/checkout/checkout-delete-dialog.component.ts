import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Checkout } from './checkout.model';
import { CheckoutPopupService } from './checkout-popup.service';
import { CheckoutService } from './checkout.service';

@Component({
    selector: 'jhi-checkout-delete-dialog',
    templateUrl: './checkout-delete-dialog.component.html'
})
export class CheckoutDeleteDialogComponent {

    checkout: Checkout;

    constructor(
        private checkoutService: CheckoutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.checkoutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'checkoutListModification',
                content: 'Deleted an checkout'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-checkout-delete-popup',
    template: ''
})
export class CheckoutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private checkoutPopupService: CheckoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.checkoutPopupService
                .open(CheckoutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
