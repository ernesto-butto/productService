import { ProductImage } from '../product-image';
import { Tag } from '../tag';
import { AppList } from '../app-list';
export class Product {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
        public description?: string,
        public shortDescription?: string,
        public model?: string,
        public weight?: number,
        public addedDate?: any,
        public minOrder?: number,
        public orderQuantity?: number,
        public price?: number,
        public sortOrder?: number,
        public productImage?: ProductImage,
        public tags?: Tag,
        public appList?: AppList,
    ) {
    }
}
