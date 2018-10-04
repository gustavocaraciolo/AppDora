import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppDoraCheckoutModule } from './checkout/checkout.module';
import { AppDoraProdutoModule } from './produto/produto.module';
import { AppDoraCategoriaModule } from './categoria/categoria.module';
import { AppDoraClienteModule } from './cliente/cliente.module';
import { AppDoraTagModule } from './tag/tag.module';
import { AppDoraItensCheckoutModule } from './itens-checkout/itens-checkout.module';
import { AppDoraPortalModule } from './portal/portal.module';
import { AppDoraNoticiaModule } from './noticia/noticia.module';
import { AppDoraUtilizadorModule } from './utilizador/utilizador.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
  imports: [
    AppDoraCheckoutModule,
    AppDoraProdutoModule,
    AppDoraCategoriaModule,
    AppDoraClienteModule,
    AppDoraTagModule,
    AppDoraItensCheckoutModule,
    AppDoraPortalModule,
    AppDoraNoticiaModule,
    AppDoraUtilizadorModule
    /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraEntityModule {}
