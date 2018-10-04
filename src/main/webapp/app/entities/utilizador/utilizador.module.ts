import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppDoraSharedModule } from 'app/shared';
import { AppDoraAdminModule } from 'app/admin/admin.module';
import {
  UtilizadorComponent,
  UtilizadorDetailComponent,
  UtilizadorUpdateComponent,
  UtilizadorDeletePopupComponent,
  UtilizadorDeleteDialogComponent,
  utilizadorRoute,
  utilizadorPopupRoute
} from './';

const ENTITY_STATES = [...utilizadorRoute, ...utilizadorPopupRoute];

@NgModule({
  imports: [AppDoraSharedModule, AppDoraAdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UtilizadorComponent,
    UtilizadorDetailComponent,
    UtilizadorUpdateComponent,
    UtilizadorDeleteDialogComponent,
    UtilizadorDeletePopupComponent
  ],
  entryComponents: [UtilizadorComponent, UtilizadorUpdateComponent, UtilizadorDeleteDialogComponent, UtilizadorDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraUtilizadorModule {}
