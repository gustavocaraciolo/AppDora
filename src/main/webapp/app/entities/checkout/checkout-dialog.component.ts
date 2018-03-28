import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Checkout } from './checkout.model';
import { CheckoutPopupService } from './checkout-popup.service';
import { CheckoutService } from './checkout.service';
import { Cliente, ClienteService } from '../cliente';
import { Produto, ProdutoService } from '../produto';

@Component({
    selector: 'jhi-checkout-dialog',
    templateUrl: './checkout-dialog.component.html'
})
export class CheckoutDialogComponent implements OnInit {

    checkout: Checkout;
    isSaving: boolean;

    clientes: Cliente[];

    produtos: Produto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private checkoutService: CheckoutService,
        private clienteService: ClienteService,
        private produtoService: ProdutoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clienteService
            .query({filter: 'checkout-is-null'})
            .subscribe((res: HttpResponse<Cliente[]>) => {
                if (!this.checkout.clienteId) {
                    this.clientes = res.body;
                } else {
                    this.clienteService
                        .find(this.checkout.clienteId)
                        .subscribe((subRes: HttpResponse<Cliente>) => {
                            this.clientes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.produtoService.query()
            .subscribe((res: HttpResponse<Produto[]>) => { this.produtos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.checkout.id !== undefined) {
            this.subscribeToSaveResponse(
                this.checkoutService.update(this.checkout));
        } else {
            this.subscribeToSaveResponse(
                this.checkoutService.create(this.checkout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Checkout>>) {
        result.subscribe((res: HttpResponse<Checkout>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Checkout) {
        this.eventManager.broadcast({ name: 'checkoutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClienteById(index: number, item: Cliente) {
        return item.id;
    }

    trackProdutoById(index: number, item: Produto) {
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
    selector: 'jhi-checkout-popup',
    template: ''
})
export class CheckoutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private checkoutPopupService: CheckoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.checkoutPopupService
                    .open(CheckoutDialogComponent as Component, params['id']);
            } else {
                this.checkoutPopupService
                    .open(CheckoutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
