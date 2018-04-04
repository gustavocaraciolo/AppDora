import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ItensCheckout } from './itens-checkout.model';
import { ItensCheckoutPopupService } from './itens-checkout-popup.service';
import { ItensCheckoutService } from './itens-checkout.service';

@Component({
    selector: 'jhi-itens-checkout-delete-dialog',
    templateUrl: './itens-checkout-delete-dialog.component.html'
})
export class ItensCheckoutDeleteDialogComponent {

    itensCheckout: ItensCheckout;

    constructor(
        private itensCheckoutService: ItensCheckoutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itensCheckoutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'itensCheckoutListModification',
                content: 'Deleted an itensCheckout'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-itens-checkout-delete-popup',
    template: ''
})
export class ItensCheckoutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itensCheckoutPopupService: ItensCheckoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.itensCheckoutPopupService
                .open(ItensCheckoutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
