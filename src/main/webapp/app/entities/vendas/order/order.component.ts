import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators, AbstractControl, FormControl} from '@angular/forms'

import {Router} from '@angular/router'

import {OrderService} from './order.service'

import {Order, OrderItem} from "./order.model"
import {CartItem} from "../carrinho/carrinho.model";
import {RadioOption} from "../../../shared/radio/radio-option.model";
import {Message, SelectItem} from "primeng/components/common/api";
import {Cliente, ClienteService} from "../../cliente";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {Checkout} from "../../checkout/checkout.model";
import {ItensCheckout, ItensCheckoutService} from "../../itens-checkout";
import {CheckoutService} from "../../checkout/checkout.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'mt-order',
    templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {

    msgs: Message[] = [];

    submitted: boolean;

    address: string;

    clientes: Cliente[];

    clientesSelectItem: SelectItem[];

    selectedCliente: any;

    clientesCodeSelectItem: SelectItem[];

    delivery: number = 8

    checkout: Checkout;

    isSaving: boolean;

    itenscheckouts: ItensCheckout[];

    constructor(private orderService: OrderService,
                private router: Router,
                private jhiAlertService: JhiAlertService,
                private checkoutService: CheckoutService,
                private clienteService: ClienteService,
                private itensCheckoutService: ItensCheckoutService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAllClientesSelectedItem();
    }

    onSubmit(value: string) {
        this.submitted = true;
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Success', detail: 'Form Submitted'});
    }

    itemsValue(): number {
        return this.orderService.itemsValue()
    }

    cartItems(): CartItem[] {
        return this.orderService.cartItems()
    }

    increaseQty(item: CartItem) {
        this.orderService.increaseQty(item)
    }

    decreaseQty(item: CartItem) {
        this.orderService.decreaseQty(item)
    }

    remove(item: CartItem) {
        this.orderService.remove(item)
    }

    checkOrder(order: Order) {
        order.orderItems = this.cartItems()
            .map((item: CartItem) => new OrderItem(item.quantity, item.menuItem.nome))
        this.orderService.checkOrder(order)
            .subscribe((orderId: string) => {
                this.router.navigate(['/order-summary'])
                this.orderService.clear()
            })
        console.log(order)
    }

    loadAllClientesSelectedItem() {
        this.clienteService.query()
            .subscribe((res: HttpResponse<Cliente[]>) => {
                    this.clientes = res.body;
                    const clienteList: any[] = [];
                    const clienteCodes: any[] = [];
                    for (const cliente of this.clientes) {
                        clienteList.push({label: cliente.name, value: {name: cliente.name, id: cliente.id}});
                        clienteCodes.push({label: cliente.id, value: {name: cliente.id, id: cliente.id}});
                    }
                    this.clientesSelectItem = clienteList;
                    this.clientesCodeSelectItem = clienteCodes;
                },
                (res: HttpErrorResponse) => this.onError(res.message));

    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    save() {
        this.checkout.clienteId = this.selectedCliente.id;
        this.isSaving = true;
        this.subscribeToSaveResponse(
            this.checkoutService.create(this.checkout));

    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Checkout>>) {
        result.subscribe((res: HttpResponse<Checkout>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Checkout) {
        this.eventManager.broadcast({ name: 'checkoutListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    trackClienteById(index: number, item: Cliente) {
        return item.id;
    }

    trackItensCheckoutById(index: number, item: ItensCheckout) {
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
