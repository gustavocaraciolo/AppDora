import {Injectable} from '@angular/core'

import {Http, Headers, RequestOptions} from '@angular/http'
import {Observable} from 'rxjs/Observable'
import 'rxjs/add/operator/map'



import {Order, OrderItem} from './order.model'
import {CarrinhoFrameService} from "../carrinho-frame/carrinho-frame.service";
import {CarrinhoFrameModel} from "../carrinho-frame/carrinho-frame.model";


@Injectable()
export class OrderService {

  constructor(private cartService: CarrinhoFrameService, private http: Http){}

  itemsValue(): number {
    return this.cartService.total()
  }

  cartItems(): CarrinhoFrameModel[]{
    return this.cartService.items
  }

  increaseQty(item: CarrinhoFrameModel){
    this.cartService.increaseQty(item)
  }

  decreaseQty(item: CarrinhoFrameModel){
    this.cartService.decreaseQty(item)
  }

  remove(item: CarrinhoFrameModel){
    this.cartService.removeItem(item)
  }

  clear(){
    this.cartService.clear()
  }

  checkOrder(order: Order): Observable<string> {
    const headers = new Headers()
    headers.append('Content-Type', 'application/json')
    return this.http.post(`${'PENDENTEEEEEE'}/orders`,
                          JSON.stringify(order),
                          new RequestOptions({headers: headers}))
                    .map(response=> response.json())
                    .map(order => order.id)
  }

}
