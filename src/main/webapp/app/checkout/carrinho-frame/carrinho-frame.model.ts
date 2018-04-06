import {Produto} from "../../entities/produto";

export class CarrinhoFrameModel {
    constructor(public produtoItem: Produto,
                public quantity: number = 1){}

    value(): number {
        return this.produtoItem.preco * this.quantity
    }
}
