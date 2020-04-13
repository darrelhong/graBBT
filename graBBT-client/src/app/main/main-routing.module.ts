import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { ListingsComponent } from './listings/listings.component'
import { AccountComponent } from './account/account.component'
import { CheckoutComponent } from './checkout/checkout.component'
import { AuthGuard } from '../auth/auth.guard'
import { CheckoutSuccessComponent } from './checkout/checkout-success/checkout-success.component'
import { CheckoutErrorComponent } from './checkout/checkout-error/checkout-error.component'

const routes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: OutletsComponent,
      },
      {
        // for testing purposes
        path: 'listing',
        component: ListingsComponent,
      },
      {
        path: 'listings/:id',
        component: ListingsComponent,
      },
      {
        path: 'account',
        component: AccountComponent,
      },
      {
        path: 'checkout',
        component: CheckoutComponent,
        children: [
          { path: 'success', component: CheckoutSuccessComponent },
          { path: 'error', component: CheckoutErrorComponent },
        ],
      },
    ],
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
