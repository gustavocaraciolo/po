import { BaseEntity } from './../../shared';

export class Request implements BaseEntity {
    constructor(
        public id?: number,
        public dateRequest?: any,
        public userExtraId?: number,
        public requestItemsId?: number,
    ) {
    }
}
