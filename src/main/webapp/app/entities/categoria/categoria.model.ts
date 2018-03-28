import { BaseEntity } from './../../shared';

export class Categoria implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public produtoId?: number,
    ) {
    }
}
