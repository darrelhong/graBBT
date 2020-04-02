import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { ListingsComponent } from './listings/listings.component'
import { AccountComponent } from './account/account.component'

const routes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    children: [
      {
        path: '',
        component: OutletsComponent,
      },
      {
        //for testing purposes
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
      }
    ],
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
