// module imports
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { MainRoutingModule } from './main-routing.module'
import { MyMaterialModule } from '../modules/material.module'
import { MatSidenavModule } from '@angular/material/sidenav'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatProgressBarModule } from '@angular/material/progress-bar'
import { MatExpansionModule } from '@angular/material/expansion'

// component imports
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { ListingsComponent } from './listings/listings.component'
import { AccountComponent } from './account/account.component'
import { DialogListingComponent } from './listings/dialog-listing/dialog-listing.component'
import { CartComponent } from './cart/cart.component'
import { ConfirmDialogComponent } from './listings/confirm-dialog/confirm-dialog.component'
import {
  MatCarouselSlide,
  MatCarouselSlideComponent,
  MatCarousel,
  MatCarouselComponent,
} from '@ngmodule/material-carousel'
import { CheckoutComponent } from './checkout/checkout.component'
import { CheckoutSuccessComponent } from './checkout/checkout-success/checkout-success.component'
import { CheckoutErrorComponent } from './checkout/checkout-error/checkout-error.component'

@NgModule({
  declarations: [
    MainComponent,
    OutletsComponent,
    ListingsComponent,
    AccountComponent,
    DialogListingComponent,
    CartComponent,
    ConfirmDialogComponent,
    MatCarouselComponent,
    MatCarouselSlideComponent,
    CheckoutComponent,
    CheckoutSuccessComponent,
    CheckoutErrorComponent,
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    MyMaterialModule,
    MatSidenavModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressBarModule,
    MatExpansionModule,
  ],
  entryComponents: [DialogListingComponent, ConfirmDialogComponent],
})
export class MainModule {}
