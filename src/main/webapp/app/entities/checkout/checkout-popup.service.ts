import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Checkout } from './checkout.model';
import { CheckoutService } from './checkout.service';

@Injectable()
export class CheckoutPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private checkoutService: CheckoutService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.checkoutService.find(id)
                    .subscribe((checkoutResponse: HttpResponse<Checkout>) => {
                        const checkout: Checkout = checkoutResponse.body;
                        checkout.dataHora = this.datePipe
                            .transform(checkout.dataHora, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.checkoutModalRef(component, checkout);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.checkoutModalRef(component, new Checkout());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    checkoutModalRef(component: Component, checkout: Checkout): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.checkout = checkout;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
