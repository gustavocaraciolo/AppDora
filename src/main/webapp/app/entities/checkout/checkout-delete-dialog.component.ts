import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Checkout } from './checkout.model';
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
export class CheckoutDeletePopupComponent implements OnInit {

    private ngbModalRef: NgbModalRef;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private modalService: NgbModal
    ) {
    }

    ngOnInit() {
        this.route.data.subscribe(({checkout}) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CheckoutDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static'});
                console.log(checkout)
                this.ngbModalRef.componentInstance.checkout = checkout.body;
                this.ngbModalRef.result.then((result) => {
                    this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
                    this.ngbModalRef = null;
                }, (reason) => {
                    this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
                    this.ngbModalRef = null;
                });
            }, 0);
        });
    }

}
