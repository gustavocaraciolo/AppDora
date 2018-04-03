import {Produto} from "../../produto";


export class CartItem {
    constructor(public menuItem: Produto,
                public quantity: number = 1){}

    value(): number {
        return this.menuItem.preco * this.quantity
    }
}
