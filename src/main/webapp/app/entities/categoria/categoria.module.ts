import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppDoraSharedModule } from '../../shared';
import {
    CategoriaService,
    CategoriaPopupService,
    CategoriaComponent,
    CategoriaDetailComponent,
    CategoriaDialogComponent,
    CategoriaPopupComponent,
    CategoriaDeletePopupComponent,
    CategoriaDeleteDialogComponent,
    categoriaRoute,
    categoriaPopupRoute,
    CategoriaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...categoriaRoute,
    ...categoriaPopupRoute,
];

@NgModule({
    imports: [
        AppDoraSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CategoriaComponent,
        CategoriaDetailComponent,
        CategoriaDialogComponent,
        CategoriaDeleteDialogComponent,
        CategoriaPopupComponent,
        CategoriaDeletePopupComponent,
    ],
    entryComponents: [
        CategoriaComponent,
        CategoriaDialogComponent,
        CategoriaPopupComponent,
        CategoriaDeleteDialogComponent,
        CategoriaDeletePopupComponent,
    ],
    providers: [
        CategoriaService,
        CategoriaPopupService,
        CategoriaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraCategoriaModule {}
