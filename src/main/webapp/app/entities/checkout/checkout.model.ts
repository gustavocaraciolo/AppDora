import { BaseEntity } from './../../shared';

export class Checkout implements BaseEntity {
    constructor(
        public id?: number,
        public dataHora?: any,
        public quantidade?: number,
        public desconto?: number,
        public clienteId?: number,
        public produtos?: BaseEntity[],
    ) {
    }
}
