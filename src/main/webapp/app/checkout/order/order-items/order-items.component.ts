import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {CarrinhoFrameModel} from "../../carrinho-frame/carrinho-frame.model";



@Component({
  selector: 'mt-order-items',
  templateUrl: './order-items.component.html'
})
export class OrderItemsComponent implements OnInit {

  @Input() items: CarrinhoFrameModel[]

  @Output() increaseQty = new EventEmitter<CarrinhoFrameModel>()
  @Output() decreaseQty = new EventEmitter<CarrinhoFrameModel>()
  @Output() remove = new EventEmitter<CarrinhoFrameModel>()

  constructor() { }

  ngOnInit() {
  }

  emitIncreaseQty(item: CarrinhoFrameModel){
    this.increaseQty.emit(item)
  }

  emitDecreaseQty(item: CarrinhoFrameModel){
    this.decreaseQty.emit(item)
  }

  emitRemove(item: CarrinhoFrameModel){
    this.remove.emit(item)
  }

}
