import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PoseidonProductModule } from './product/product.module';
import { PoseidonCategoryModule } from './category/category.module';
import { PoseidonRequestModule } from './request/request.module';
import { PoseidonRequestItemsModule } from './request-items/request-items.module';
import { PoseidonUserExtraModule } from './user-extra/user-extra.module';
import { PoseidonTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PoseidonProductModule,
        PoseidonCategoryModule,
        PoseidonRequestModule,
        PoseidonRequestItemsModule,
        PoseidonUserExtraModule,
        PoseidonTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PoseidonEntityModule {}
