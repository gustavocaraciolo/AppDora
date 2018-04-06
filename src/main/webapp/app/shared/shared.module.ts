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
import {RadioComponent} from "./radio/radio.component";
import {InputComponent} from "./input/input.component";
import {RatingComponent} from "./rating/rating.component";

@NgModule({
    imports: [
        AppDoraSharedLibsModule,
        AppDoraSharedCommonModule
    ],
    declarations: [
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        SnackbarComponent,
        RadioComponent,
        InputComponent,
        RatingComponent,
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
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        AppDoraSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        SnackbarComponent,
        RadioComponent,
        InputComponent,
        RatingComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class AppDoraSharedModule {}
