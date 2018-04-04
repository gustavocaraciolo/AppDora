import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ItensCheckout } from './itens-checkout.model';
import { ItensCheckoutService } from './itens-checkout.service';

@Injectable()
export class ItensCheckoutPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private itensCheckoutService: ItensCheckoutService

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
                this.itensCheckoutService.find(id)
                    .subscribe((itensCheckoutResponse: HttpResponse<ItensCheckout>) => {
                        const itensCheckout: ItensCheckout = itensCheckoutResponse.body;
                        this.ngbModalRef = this.itensCheckoutModalRef(component, itensCheckout);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.itensCheckoutModalRef(component, new ItensCheckout());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    itensCheckoutModalRef(component: Component, itensCheckout: ItensCheckout): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.itensCheckout = itensCheckout;
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
