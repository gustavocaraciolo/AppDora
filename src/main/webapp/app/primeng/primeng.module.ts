
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppDoraButtonDemoModule } from './buttons/button/buttondemo.module';
import { AppDoraSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { AppDoraDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { AppDoraConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { AppDoraLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { AppDoraTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { AppDoraOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { AppDoraSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { AppDoraKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { AppDoraInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { AppDoraInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { AppDoraInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { AppDoraCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { AppDoraCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { AppDoraChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { AppDoraColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { AppDoraInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { AppDoraInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { AppDoraPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { AppDoraAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { AppDoraSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { AppDoraSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { AppDoraRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { AppDoraSelectDemoModule } from './inputs/select/selectdemo.module';
import { AppDoraSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { AppDoraListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { AppDoraRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { AppDoraToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { AppDoraEditorDemoModule } from './inputs/editor/editordemo.module';

import { AppDoraGrowlDemoModule } from './messages/growl/growldemo.module';
import { AppDoraMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { AppDoraGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { AppDoraFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { AppDoraAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { AppDoraPanelDemoModule } from './panel/panel/paneldemo.module';
import { AppDoraTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { AppDoraFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { AppDoraToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { AppDoraGridDemoModule } from './panel/grid/griddemo.module';
import { AppDoraScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { AppDoraCardDemoModule } from './panel/card/carddemo.module';

import { AppDoraDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { AppDoraTableDemoModule } from './data/table/tabledemo.module';
import { AppDoraDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { AppDoraDataListDemoModule } from './data/datalist/datalistdemo.module';
import { AppDoraDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { AppDoraPickListDemoModule } from './data/picklist/picklistdemo.module';
import { AppDoraOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { AppDoraScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { AppDoraTreeDemoModule } from './data/tree/treedemo.module';
import { AppDoraTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { AppDoraPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { AppDoraGmapDemoModule } from './data/gmap/gmapdemo.module';
import { AppDoraOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { AppDoraCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { AppDoraDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { AppDoraBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { AppDoraDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { AppDoraLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { AppDoraPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { AppDoraPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { AppDoraRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { AppDoraDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { AppDoraMenuDemoModule } from './menu/menu/menudemo.module';
import { AppDoraContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { AppDoraPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { AppDoraStepsDemoModule } from './menu/steps/stepsdemo.module';
import { AppDoraTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { AppDoraBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { AppDoraMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { AppDoraMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { AppDoraSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { AppDoraTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { AppDoraBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { AppDoraCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { AppDoraDeferDemoModule } from './misc/defer/deferdemo.module';
import { AppDoraInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { AppDoraProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { AppDoraRTLDemoModule } from './misc/rtl/rtldemo.module';
import { AppDoraTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { AppDoraValidationDemoModule } from './misc/validation/validationdemo.module';
import { AppDoraProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        AppDoraMenuDemoModule,
        AppDoraContextMenuDemoModule,
        AppDoraPanelMenuDemoModule,
        AppDoraStepsDemoModule,
        AppDoraTieredMenuDemoModule,
        AppDoraBreadcrumbDemoModule,
        AppDoraMegaMenuDemoModule,
        AppDoraMenuBarDemoModule,
        AppDoraSlideMenuDemoModule,
        AppDoraTabMenuDemoModule,

        AppDoraBlockUIDemoModule,
        AppDoraCaptchaDemoModule,
        AppDoraDeferDemoModule,
        AppDoraInplaceDemoModule,
        AppDoraProgressBarDemoModule,
        AppDoraInputMaskDemoModule,
        AppDoraRTLDemoModule,
        AppDoraTerminalDemoModule,
        AppDoraValidationDemoModule,

        AppDoraButtonDemoModule,
        AppDoraSplitbuttonDemoModule,

        AppDoraInputTextDemoModule,
        AppDoraInputTextAreaDemoModule,
        AppDoraInputGroupDemoModule,
        AppDoraCalendarDemoModule,
        AppDoraChipsDemoModule,
        AppDoraInputMaskDemoModule,
        AppDoraInputSwitchDemoModule,
        AppDoraPasswordIndicatorDemoModule,
        AppDoraAutoCompleteDemoModule,
        AppDoraSliderDemoModule,
        AppDoraSpinnerDemoModule,
        AppDoraRatingDemoModule,
        AppDoraSelectDemoModule,
        AppDoraSelectButtonDemoModule,
        AppDoraListboxDemoModule,
        AppDoraRadioButtonDemoModule,
        AppDoraToggleButtonDemoModule,
        AppDoraEditorDemoModule,
        AppDoraColorPickerDemoModule,
        AppDoraCheckboxDemoModule,
        AppDoraKeyFilterDemoModule,

        AppDoraGrowlDemoModule,
        AppDoraMessagesDemoModule,
        AppDoraGalleriaDemoModule,

        AppDoraFileUploadDemoModule,

        AppDoraAccordionDemoModule,
        AppDoraPanelDemoModule,
        AppDoraTabViewDemoModule,
        AppDoraFieldsetDemoModule,
        AppDoraToolbarDemoModule,
        AppDoraGridDemoModule,
        AppDoraScrollPanelDemoModule,
        AppDoraCardDemoModule,

        AppDoraBarchartDemoModule,
        AppDoraDoughnutchartDemoModule,
        AppDoraLinechartDemoModule,
        AppDoraPiechartDemoModule,
        AppDoraPolarareachartDemoModule,
        AppDoraRadarchartDemoModule,

        AppDoraDragDropDemoModule,

        AppDoraDialogDemoModule,
        AppDoraConfirmDialogDemoModule,
        AppDoraLightboxDemoModule,
        AppDoraTooltipDemoModule,
        AppDoraOverlayPanelDemoModule,
        AppDoraSideBarDemoModule,

        AppDoraDataTableDemoModule,
        AppDoraTableDemoModule,
        AppDoraDataGridDemoModule,
        AppDoraDataListDemoModule,
        AppDoraDataViewDemoModule,
        AppDoraDataScrollerDemoModule,
        AppDoraScheduleDemoModule,
        AppDoraOrderListDemoModule,
        AppDoraPickListDemoModule,
        AppDoraTreeDemoModule,
        AppDoraTreeTableDemoModule,
        AppDoraPaginatorDemoModule,
        AppDoraOrgChartDemoModule,
        AppDoraGmapDemoModule,
        AppDoraCarouselDemoModule,
        AppDoraProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDoraprimengModule {}
