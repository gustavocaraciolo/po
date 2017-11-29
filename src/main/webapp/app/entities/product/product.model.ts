import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: number,
        public unitPrice?: number,
        public quantityInStock?: number,
        public requestQuantity?: number,
        public categoryId?: number,
        public requestItems?: BaseEntity[],
    ) {
    }
}
