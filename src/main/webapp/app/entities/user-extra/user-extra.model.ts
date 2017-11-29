import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public telephone?: string,
        public userId?: number,
        public tagId?: number,
        public requests?: BaseEntity[],
    ) {
    }
}
