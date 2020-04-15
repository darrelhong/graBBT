import { BrowserModule } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatSidenavModule } from '@angular/material/sidenav'

import { MyMaterialModule } from './modules/material.module'

import { IndexComponent } from './components/index/index.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
import {
  LandingpageComponent,
  ItemDialogComponent,
} from './components/landingpage/landingpage.component'
import { AccountSheetComponent } from './components/account-sheet/account-sheet.component'
import { MainModule } from './main/main.module';
import { ErrorComponent } from './components/error/error.component';
import { PromoComponent } from './main/promo/promo.component';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    LoginComponent,
    SignupComponent,
    LandingpageComponent,
    AccountSheetComponent,
    ItemDialogComponent,
    ErrorComponent,
    PromoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MyMaterialModule,
    MatSidenavModule,
    HttpClientModule,
    MainModule,
  ],
  entryComponents: [AccountSheetComponent, ItemDialogComponent],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
