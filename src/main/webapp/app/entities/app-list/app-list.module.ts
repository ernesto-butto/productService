import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProductServiceSharedModule } from '../../shared';
import { ProductServiceAdminModule } from '../../admin/admin.module';

import {
    AppListService,
    AppListPopupService,
    AppListComponent,
    AppListDetailComponent,
    AppListDialogComponent,
    AppListPopupComponent,
    AppListDeletePopupComponent,
    AppListDeleteDialogComponent,
    appListRoute,
    appListPopupRoute,
} from './';

let ENTITY_STATES = [
    ...appListRoute,
    ...appListPopupRoute,
];

@NgModule({
    imports: [
        ProductServiceSharedModule,
        ProductServiceAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AppListComponent,
        AppListDetailComponent,
        AppListDialogComponent,
        AppListDeleteDialogComponent,
        AppListPopupComponent,
        AppListDeletePopupComponent,
    ],
    entryComponents: [
        AppListComponent,
        AppListDialogComponent,
        AppListPopupComponent,
        AppListDeleteDialogComponent,
        AppListDeletePopupComponent,
    ],
    providers: [
        AppListService,
        AppListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductServiceAppListModule {}
