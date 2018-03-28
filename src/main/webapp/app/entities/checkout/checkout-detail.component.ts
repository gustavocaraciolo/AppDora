import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Checkout } from './checkout.model';
import { CheckoutService } from './checkout.service';

@Component({
    selector: 'jhi-checkout-detail',
    templateUrl: './checkout-detail.component.html'
})
export class CheckoutDetailComponent implements OnInit, OnDestroy {

    checkout: Checkout;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private checkoutService: CheckoutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCheckouts();
    }

    load(id) {
        this.checkoutService.find(id)
            .subscribe((checkoutResponse: HttpResponse<Checkout>) => {
                this.checkout = checkoutResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCheckouts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'checkoutListModification',
            (response) => this.load(this.checkout.id)
        );
    }
}
