import {Produto} from "../../entities/produto";
import {Injectable} from "@angular/core";
import {NotificationService} from "../../shared/messages/notification.service";
import {CarrinhoFrameModel} from "./carrinho-frame.model";

@Injectable()
export class CarrinhoFrameService {
    items: CarrinhoFrameModel[] = []

    constructor(private notificationService: NotificationService){}

    clear(){
        this.items = []
    }

    addItem(item:Produto){
        let foundItem = this.items.find((mItem)=> mItem.produtoItem.id === item.id)
        if(foundItem){
            this.increaseQty(foundItem)
        }else{
            this.items.push(new CarrinhoFrameModel(item))
        }
        this.notificationService.notify(`Você adicionou o item ${item.nome}`)
    }

    increaseQty(item: CarrinhoFrameModel){
        item.quantity = item.quantity + 1
    }

    decreaseQty(item: CarrinhoFrameModel){
        item.quantity = item.quantity - 1
        if (item.quantity === 0){
            this.removeItem(item)
        }
    }

    removeItem(item:CarrinhoFrameModel){
        this.items.splice(this.items.indexOf(item), 1)
        this.notificationService.notify(`Você removeu o item ${item.produtoItem.nome}`)
    }

    total(): number{
        return this.items
            .map(item => item.value())
            .reduce((prev, value)=> prev+value, 0)
    }
}
