import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'

const routes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    children: [
      {
        path: '',
        component: OutletsComponent,
      },
    ],
  },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
