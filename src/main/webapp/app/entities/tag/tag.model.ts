import { AppList } from '../app-list';
import { Product } from '../product';
export class Tag {
    constructor(
        public id?: number,
        public name?: string,
        public appList?: AppList,
        public product?: Product,
    ) {
    }
}
