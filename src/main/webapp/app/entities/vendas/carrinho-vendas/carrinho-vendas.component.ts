import { Component, OnInit } from '@angular/core';
import {Produto, ProdutoService} from "../../produto";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Subscription} from "rxjs/Subscription";
import {ITEMS_PER_PAGE, Principal} from "../../../shared";
import {JhiAlertService, JhiEventManager, JhiParseLinks} from "ng-jhipster";

import {Vendas} from "../vendas.model";


@Component({
  selector: 'jhi-carrinho-vendas',
  templateUrl: './carrinho-vendas.component.html',
  styles: []
})
export class CarrinhoVendasComponent implements OnInit {

    produtos: Produto[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private produtoService: ProdutoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.produtoService.search({
                query: this.currentSearch,
            }).subscribe(
                (res: HttpResponse<Vendas[]>) => this.produtos = res.body,
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            return;
        }
        this.produtoService.query().subscribe(
            (res: HttpResponse<Vendas[]>) => {
                this.produtos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVendas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Vendas) {
        return item.id;
    }
    registerChangeInVendas() {
        this.eventSubscriber = this.eventManager.subscribe('vendasListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
    addMenuItem(item: Produto){
        console.log(item)
    }
}
