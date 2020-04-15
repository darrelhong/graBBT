import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { IndexComponent } from './components/index/index.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
import { LandingpageComponent } from './components/landingpage/landingpage.component'
import { ErrorComponent } from './components/error/error.component'
import { PromoComponent } from './main/promo/promo.component'

const routes: Routes = [
  { path: '', redirectTo: '/index', pathMatch: 'full' },
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'landing', component: LandingpageComponent },
  { path: 'error', component: ErrorComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
