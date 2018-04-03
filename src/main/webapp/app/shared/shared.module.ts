import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    AppDoraSharedLibsModule,
    AppDoraSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    JhiLoginModalComponent,
    Principal,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
} from './';
import {SnackbarComponent} from "./messages/snackbar/snackbar.component";
import {NotificationService} from "./messages/notification.service";
import {CarrinhoService} from "../entities/vendas/carrinho/carrinho.service";

@NgModule({
    imports: [
        AppDoraSharedLibsModule,
        AppDoraSharedCommonModule
    ],
    declarations: [
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        SnackbarComponent
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe,
        NotificationService,
        CarrinhoService
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        AppDoraSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        SnackbarComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class AppDoraSharedModule {}
