//module imports
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { MainRoutingModule } from './main-routing.module'
import { MyMaterialModule } from '../modules/material.module'
import { MatSidenavModule } from '@angular/material/sidenav'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

//component imports
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { ListingsComponent } from './listings/listings.component'
import { AccountComponent } from './account/account.component'
import { DialogListingComponent } from './listings/dialog-listing/dialog-listing.component'
import { MatCarouselSlide, MatCarouselSlideComponent, MatCarousel, MatCarouselComponent } from '@ngmodule/material-carousel';

@NgModule({
  declarations: [
    MainComponent,
    OutletsComponent,
    ListingsComponent,
    AccountComponent,
    DialogListingComponent,
    MatCarouselComponent,
    MatCarouselSlideComponent
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
