import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router'
import {OrderService} from './order.service'
import {Order, OrderItem} from "./order.model"
import {Message, SelectItem} from "primeng/components/common/api";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Observable} from "rxjs/Observable";
import {CarrinhoFrameModel} from "../carrinho-frame/carrinho-frame.model";
import {Cliente, ClienteService} from "../../entities/cliente";
import {Checkout, CheckoutService} from "../../entities/checkout";
import {ItensCheckout, ItensCheckoutService} from "../../entities/itens-checkout";
import {FormaDePagamento} from "../../entities/checkout/checkout.model";

@Component({
    selector: 'mt-order',
    templateUrl: './order.component.html',
    styleUrls: [
        '../../../content/css/style.css'
    ]
})
export class OrderComponent implements OnInit {

    types: SelectItem[];

    msgs: Message[] = [];

    submitted: boolean;

    clientes: Cliente[];

    clientesSelectItem: SelectItem[];

    selectedCliente: any;

    clientesCodeSelectItem: SelectItem[];

    delivery: number = 8

    checkout: Checkout;

    isSaving: boolean;

    itenscheckouts: ItensCheckout[] = [];

    constructor(private orderService: OrderService,
                private router: Router,
                private jhiAlertService: JhiAlertService,
                private checkoutService: CheckoutService,
                private clienteService: ClienteService,
                private itensCheckoutService: ItensCheckoutService,
                private eventManager: JhiEventManager) {
        this.checkout = new Checkout();
        this.types = [
            {label: 'Dinheiro', value: 'DINHEIRO', icon: 'fa fa-fw fa-cc-paypal'},
            {label: 'Parcelado', value: 'PARCELADO', icon: 'fa fa-fw fa-cc-visa'}
        ];
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

    cartItems(): CarrinhoFrameModel[] {
        return this.orderService.cartItems()
    }

    increaseQty(item: CarrinhoFrameModel) {
        this.orderService.increaseQty(item)
    }

    decreaseQty(item: CarrinhoFrameModel) {
        this.orderService.decreaseQty(item)
    }

    remove(item: CarrinhoFrameModel) {
        this.orderService.remove(item)
    }

    checkOrder(order: Order) {
        order.orderItems = this.cartItems()
            .map((item: CarrinhoFrameModel) => new OrderItem(item.quantity, item.produtoItem.nome))
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
        this.isSaving = true;
        if (this.checkout.id !== undefined) {
            this.subscribeToSaveResponseCheckout(
                this.checkoutService.update(this.checkout));
        } else {
            for (const produto of this.cartItems()){
                let itensCheckout = new ItensCheckout(null, produto.quantity, produto.quantity);
                this.subscribeToSaveResponseItensCheckout(
                    this.itensCheckoutService.create(itensCheckout));

            }
            this.checkout.itensCheckouts = this.itenscheckouts;
            this.checkout.precoTotal = this.itemsValue();
            this.checkout.clienteId = this.selectedCliente.id;
            this.subscribeToSaveResponseCheckout(
                this.checkoutService.create(this.checkout));
        }
    }

    public formaPagamentoEvent(obj:any){
        this.checkout.formaPagamento = obj.value;
    }
    private subscribeToSaveResponseItensCheckout(result: Observable<HttpResponse<ItensCheckout>>) {
        result.subscribe((res: HttpResponse<ItensCheckout>) =>{
                this.itenscheckouts.push(res.body);
            this.onSaveSuccessItensCheckout(res.body);
            },
            (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponseCheckout(result: Observable<HttpResponse<Checkout>>) {
        result.subscribe((res: HttpResponse<Checkout>) =>
            this.onSaveSuccessCheckout(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccessCheckout(result: Checkout) {
        this.eventManager.broadcast({ name: 'checkoutListModification', content: 'OK'});
    }

    private onSaveSuccessItensCheckout(result: ItensCheckout) {
        this.eventManager.broadcast({ name: 'checkoutListModification', content: 'OK'});
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
