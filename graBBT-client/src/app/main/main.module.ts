import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'

import { MainRoutingModule } from './main-routing.module'
import { MainComponent } from './main/main.component'
import { OutletsComponent } from './outlets/outlets.component'
import { MyMaterialModule } from '../modules/material.module'
import { MatSidenavModule } from '@angular/material/sidenav'

@NgModule({
  declarations: [MainComponent, OutletsComponent],
  imports: [
    CommonModule,
    MainRoutingModule,
    MyMaterialModule,
    MatSidenavModule,
  ],
})
export class MainModule {}
