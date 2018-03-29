import { BaseEntity } from './../../shared';

export class Cliente implements BaseEntity {
    constructor(
        public id?: number,
        public telefone?: string,
        public userId?: number,
        public name?: string,
        public email?: string,
        public tagId?: number,
        public checkouts?: BaseEntity[],
    ) {
    }
}
