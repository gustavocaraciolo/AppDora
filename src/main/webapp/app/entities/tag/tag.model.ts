import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public clientes?: BaseEntity[],
    ) {
    }
}
