import { Product } from '../product';
export class ProductImage {
    constructor(
        public id?: number,
        public title?: string,
        public image?: any,
        public product?: Product,
    ) {
    }
}
