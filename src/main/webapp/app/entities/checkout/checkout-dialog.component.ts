import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Checkout } from './checkout.model';
import { CheckoutService } from './checkout.service';
import { Cliente, ClienteService } from '../cliente';
import { Produto, ProdutoService } from '../produto';
import {MASK_MOEDA_KWANZA} from '../../app.constants';
import Country from "../../primeng/inputs/autocomplete/service/country";

@Component({
    selector: 'jhi-checkout-dialog',
    templateUrl: './checkout-dialog.component.html'
})
export class CheckoutDialogComponent implements OnInit {

    checkout: Checkout;
    isSaving: boolean;

    clientes: Cliente[];

    produtos: Produto[];

    public maskMoeda = MASK_MOEDA_KWANZA;

    constructor(
        private jhiAlertService: JhiAlertService,
        private checkoutService: CheckoutService,
        private clienteService: ClienteService,
        private produtoService: ProdutoService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private router: Router

    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({checkout}) => {
            this.checkout = checkout.body ? checkout.body : checkout;
        });

        this.clienteService.query()
            .subscribe((res: HttpResponse<Cliente[]>) => { this.clientes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.produtoService.query()
            .subscribe((res: HttpResponse<Produto[]>) => { this.produtos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    filterProdutos(event: any) {
        const query = event.query;
        this.produtoService.query().subscribe((res: HttpResponse<Produto[]>) => {
            this.produtos = this.filterProduto(query, res.body);
                }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    filterProduto(query: any, produtos: Produto[]): Produto[] {
        const filtered: any[] = [];
        for (const produto of produtos) {
            if (produto.nome.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(produto);
            }
        }
        return filtered;
    }

    private onSaveSuccess(result: Checkout) {
        this.isSaving = false;
        this.previousState();
    }

    previousState() {
        window.history.back();
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
