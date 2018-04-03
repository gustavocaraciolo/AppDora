import {NgModule} from "@angular/core"
import {RouterModule, Routes} from '@angular/router'

import {FormsModule, ReactiveFormsModule} from '@angular/forms'

import {OrderComponent} from './order.component'
import {OrderItemsComponent} from './order-items/order-items.component'
import {DeliveryCostsComponent} from './delivery-costs/delivery-costs.component'
import {SharedModule} from "primeng/shared";
import {CommonModule} from '@angular/common';
const ROUTES: Routes = [
  {path:'', component: OrderComponent}
]

@NgModule({
  declarations:[OrderComponent,OrderItemsComponent,DeliveryCostsComponent],
  imports: [SharedModule, RouterModule.forChild(ROUTES),FormsModule,ReactiveFormsModule, CommonModule]
})
export class OrderModule {}
