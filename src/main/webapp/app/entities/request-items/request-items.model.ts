import { BaseEntity } from './../../shared';

export class RequestItems implements BaseEntity {
    constructor(
        public id?: number,
        public discount?: number,
        public pedidos?: BaseEntity[],
        public productId?: number,
    ) {
    }
}
