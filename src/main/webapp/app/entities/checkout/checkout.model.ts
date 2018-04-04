import { BaseEntity } from './../../shared';

export const enum FormaDePagamento {
    'DINHEIRO',
    'PARCELADO'
}

export class Checkout implements BaseEntity {
    constructor(
        public id?: number,
        public dataHora?: any,
        public desconto?: number,
        public precoTotal?: number,
        public formaPagamento?: FormaDePagamento,
        public clienteId?: number,
        public itensCheckouts?: BaseEntity[],
    ) {
    }
}
