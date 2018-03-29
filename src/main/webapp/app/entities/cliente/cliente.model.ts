import { BaseEntity } from './../../shared';

export class Cliente implements BaseEntity {
    constructor(
        public id?: number,
        public telefone?: string,
        public userId?: number,
        public checkoutId?: number,
        public tagId?: number,
    ) {
    }
}
