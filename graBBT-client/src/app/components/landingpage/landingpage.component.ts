import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout'
import { map, shareReplay } from 'rxjs/operators'
import { SessionService } from 'src/app/services/session.service'
import { Router } from '@angular/router'
import { MatBottomSheet } from '@angular/material/bottom-sheet'

import { AccountSheetComponent } from '../account-sheet/account-sheet.component'

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css'],
})
export class LandingpageComponent implements OnInit {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    )

  username: string

  constructor(
    private breakpointObserver: BreakpointObserver,
    private sessionService: SessionService,
    private router: Router,
    private bottomSheet: MatBottomSheet
  ) {}

  ngOnInit() {
    this.username = this.sessionService.getCurrentCustomer().name
  }

  logout() {
    this.sessionService.setCurrentCustomer(null)
    this.sessionService.setCustomerIsLogin(null)

    this.router.navigate(['/index'])
  }

  openBottomSheet(): void {
    this.bottomSheet.open(AccountSheetComponent)
  }
}
