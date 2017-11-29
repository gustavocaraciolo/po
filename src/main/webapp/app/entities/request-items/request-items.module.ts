import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PoseidonSharedModule } from '../../shared';
import {
    RequestItemsService,
    RequestItemsPopupService,
    RequestItemsComponent,
    RequestItemsDetailComponent,
    RequestItemsDialogComponent,
    RequestItemsPopupComponent,
    RequestItemsDeletePopupComponent,
    RequestItemsDeleteDialogComponent,
    requestItemsRoute,
    requestItemsPopupRoute,
    RequestItemsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...requestItemsRoute,
    ...requestItemsPopupRoute,
];

@NgModule({
    imports: [
        PoseidonSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RequestItemsComponent,
        RequestItemsDetailComponent,
        RequestItemsDialogComponent,
        RequestItemsDeleteDialogComponent,
        RequestItemsPopupComponent,
        RequestItemsDeletePopupComponent,
    ],
    entryComponents: [
        RequestItemsComponent,
        RequestItemsDialogComponent,
        RequestItemsPopupComponent,
        RequestItemsDeleteDialogComponent,
        RequestItemsDeletePopupComponent,
    ],
    providers: [
        RequestItemsService,
        RequestItemsPopupService,
        RequestItemsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoseidonRequestItemsModule {}
