import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core'
import { Observable } from 'rxjs'
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout'
import { map, shareReplay } from 'rxjs/operators'
import { SessionService } from 'src/app/services/session.service'
import { Router } from '@angular/router'
import { MatBottomSheet } from '@angular/material/bottom-sheet'

import { AccountSheetComponent } from 'src/app//components/account-sheet/account-sheet.component'
import { Cart } from 'src/app/services/cart/cart'
import { CartService } from 'src/app/services/cart/cart.service'
import { SidenavService } from 'src/app/services/sidenav.service'
import { MatSidenav } from '@angular/material'

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit, AfterViewInit {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    )
  @ViewChild('sidenav', { static: true }) sidenav: MatSidenav

  name: string
  cart: Cart

  constructor(
    private breakpointObserver: BreakpointObserver,
    private sessionService: SessionService,
    private router: Router,
    private bottomSheet: MatBottomSheet,
    private cartService: CartService,
    private sidenavService: SidenavService
  ) {}

  ngOnInit() {
    this.name = this.sessionService.getCurrentCustomer().name
    this.cartService.cart.subscribe(cart => (this.cart = cart))
  }

  ngAfterViewInit() {
    this.sidenavService.setSidenav(this.sidenav)
  }

  logout() {
    this.sessionService.setCurrentCustomer(null)
    this.sessionService.setCustomerIsLogin(null)

    this.router.navigate(['/index'])
  }

  openBottomSheet(): void {
    this.bottomSheet.open(AccountSheetComponent)
  }

  //find a way to access this
  closeBottomSheet(): void {
    this.bottomSheet.dismiss()
  }
}
