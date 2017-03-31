import { Product } from '../product';
import { Tag } from '../tag';
import { User } from '../../shared';
export class AppList {
    constructor(
        public id?: number,
        public name?: string,
        public registration?: any,
        public jsonconfig?: string,
        public isEnabled?: boolean,
        public product?: Product,
        public tag?: Tag,
        public user?: User,
    ) {
        this.isEnabled = false;
    }
}
