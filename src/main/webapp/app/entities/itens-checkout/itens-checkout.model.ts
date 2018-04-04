import { BaseEntity } from './../../shared';

export class ItensCheckout implements BaseEntity {
    constructor(
        public id?: number,
        public quantidade?: number,
        public produtoId?: number,
        public checkouts?: BaseEntity[],
    ) {
    }
}
