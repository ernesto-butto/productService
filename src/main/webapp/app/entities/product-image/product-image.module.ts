import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProductServiceSharedModule } from '../../shared';

import {
    ProductImageService,
    ProductImagePopupService,
    ProductImageComponent,
    ProductImageDetailComponent,
    ProductImageDialogComponent,
    ProductImagePopupComponent,
    ProductImageDeletePopupComponent,
    ProductImageDeleteDialogComponent,
    productImageRoute,
    productImagePopupRoute,
} from './';

let ENTITY_STATES = [
    ...productImageRoute,
    ...productImagePopupRoute,
];

@NgModule({
    imports: [
        ProductServiceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductImageComponent,
        ProductImageDetailComponent,
        ProductImageDialogComponent,
        ProductImageDeleteDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeletePopupComponent,
    ],
    entryComponents: [
        ProductImageComponent,
        ProductImageDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeleteDialogComponent,
        ProductImageDeletePopupComponent,
    ],
    providers: [
        ProductImageService,
        ProductImagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductServiceProductImageModule {}
