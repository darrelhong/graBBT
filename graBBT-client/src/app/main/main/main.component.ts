import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs'
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout'
import { map, shareReplay } from 'rxjs/operators'
import { SessionService } from 'src/app/services/session.service'
import { Router } from '@angular/router'
import { MatBottomSheet } from '@angular/material/bottom-sheet'

import { AccountSheetComponent } from 'src/app//components/account-sheet/account-sheet.component'

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    )

  name: string

  constructor(
    private breakpointObserver: BreakpointObserver,
    private sessionService: SessionService,
    private router: Router,
    private bottomSheet: MatBottomSheet
  ) {}

  ngOnInit() {
    this.name = this.sessionService.getCurrentCustomer().name
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
