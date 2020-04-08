import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'

import { MainRoutingModule } from './main-routing.module'
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { MyMaterialModule } from '../modules/material.module'
import { MatSidenavModule } from '@angular/material/sidenav'
import { ListingsComponent } from './listings/listings.component'
import { AccountComponent } from './account/account.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { DialogListingComponent } from './listings/dialog-listing/dialog-listing.component'

@NgModule({
  declarations: [
    MainComponent,
    OutletsComponent,
    ListingsComponent,
    AccountComponent,
    DialogListingComponent,
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    MyMaterialModule,
    MatSidenavModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  entryComponents: [DialogListingComponent],
})
export class MainModule {}
