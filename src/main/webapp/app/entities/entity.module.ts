import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ProductServiceAppListModule } from './app-list/app-list.module';
import { ProductServiceProductModule } from './product/product.module';
import { ProductServiceProductImageModule } from './product-image/product-image.module';
import { ProductServiceTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ProductServiceAppListModule,
        ProductServiceProductModule,
        ProductServiceProductImageModule,
        ProductServiceTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductServiceEntityModule {}
