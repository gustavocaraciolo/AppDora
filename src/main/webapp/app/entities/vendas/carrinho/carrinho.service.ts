import { Injectable } from '@angular/core';
import {CartItem} from "./carrinho.model";
import {Produto} from "../../produto";
import {NotificationService} from "../../../shared/messages/notification.service";

@Injectable()
export class CarrinhoService {
    items: CartItem[] = []

    constructor(private notificationService: NotificationService){}

    clear(){
        this.items = []
    }

    addItem(item:Produto){
        let foundItem = this.items.find((mItem)=> mItem.menuItem.id === item.id)
        if(foundItem){
            this.increaseQty(foundItem)
        }else{
            this.items.push(new CartItem(item))
        }
        this.notificationService.notify(`Você adicionou o item ${item.nome}`)
    }

    increaseQty(item: CartItem){
        item.quantity = item.quantity + 1
    }

    decreaseQty(item: CartItem){
        item.quantity = item.quantity - 1
        if (item.quantity === 0){
            this.removeItem(item)
        }
    }

    removeItem(item:CartItem){
        this.items.splice(this.items.indexOf(item), 1)
        this.notificationService.notify(`Você removeu o item ${item.menuItem.nome}`)
    }

    total(): number{
        return this.items
            .map(item => item.value())
            .reduce((prev, value)=> prev+value, 0)
    }
}
