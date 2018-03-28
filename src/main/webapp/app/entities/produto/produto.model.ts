import { BaseEntity } from './../../shared';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public quantidade?: number,
        public preco?: number,
        public categoriaId?: number,
        public checkouts?: BaseEntity[],
    ) {
    }
}
