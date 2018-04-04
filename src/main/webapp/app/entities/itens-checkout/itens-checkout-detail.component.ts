import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ItensCheckout } from './itens-checkout.model';
import { ItensCheckoutService } from './itens-checkout.service';

@Component({
    selector: 'jhi-itens-checkout-detail',
    templateUrl: './itens-checkout-detail.component.html'
})
export class ItensCheckoutDetailComponent implements OnInit, OnDestroy {

    itensCheckout: ItensCheckout;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private itensCheckoutService: ItensCheckoutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInItensCheckouts();
    }

    load(id) {
        this.itensCheckoutService.find(id)
            .subscribe((itensCheckoutResponse: HttpResponse<ItensCheckout>) => {
                this.itensCheckout = itensCheckoutResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInItensCheckouts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'itensCheckoutListModification',
            (response) => this.load(this.itensCheckout.id)
        );
    }
}
