import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppDoraSharedModule } from 'app/shared';
import {
  NoticiaComponent,
  NoticiaDetailComponent,
  NoticiaUpdateComponent,
  NoticiaDeletePopupComponent,
  NoticiaDeleteDialogComponent,
  noticiaRoute,
  noticiaPopupRoute
} from './';

const ENTITY_STATES = [...noticiaRoute, ...noticiaPopupRoute];

@NgModule({
  imports: [AppDoraSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NoticiaComponent,
    NoticiaDetailComponent,
    NoticiaUpdateComponent,
    NoticiaDeleteDialogComponent,
    NoticiaDeletePopupComponent
  ],
  entryComponents: [NoticiaComponent, NoticiaUpdateComponent, NoticiaDeleteDialogComponent, NoticiaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraNoticiaModule {}
