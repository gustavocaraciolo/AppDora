import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ItensCheckout } from './itens-checkout.model';
import { ItensCheckoutPopupService } from './itens-checkout-popup.service';
import { ItensCheckoutService } from './itens-checkout.service';
import { Produto, ProdutoService } from '../produto';
import { Checkout, CheckoutService } from '../checkout';

@Component({
    selector: 'jhi-itens-checkout-dialog',
    templateUrl: './itens-checkout-dialog.component.html'
})
export class ItensCheckoutDialogComponent implements OnInit {

    itensCheckout: ItensCheckout;
    isSaving: boolean;

    produtos: Produto[];

    checkouts: Checkout[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private itensCheckoutService: ItensCheckoutService,
        private produtoService: ProdutoService,
        private checkoutService: CheckoutService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.produtoService.query()
            .subscribe((res: HttpResponse<Produto[]>) => { this.produtos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.checkoutService.query()
            .subscribe((res: HttpResponse<Checkout[]>) => { this.checkouts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.itensCheckout.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itensCheckoutService.update(this.itensCheckout));
        } else {
            this.subscribeToSaveResponse(
                this.itensCheckoutService.create(this.itensCheckout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ItensCheckout>>) {
        result.subscribe((res: HttpResponse<ItensCheckout>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ItensCheckout) {
        this.eventManager.broadcast({ name: 'itensCheckoutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProdutoById(index: number, item: Produto) {
        return item.id;
    }

    trackCheckoutById(index: number, item: Checkout) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-itens-checkout-popup',
    template: ''
})
export class ItensCheckoutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itensCheckoutPopupService: ItensCheckoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.itensCheckoutPopupService
                    .open(ItensCheckoutDialogComponent as Component, params['id']);
            } else {
                this.itensCheckoutPopupService
                    .open(ItensCheckoutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
