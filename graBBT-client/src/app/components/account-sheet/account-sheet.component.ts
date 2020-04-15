import { Component, OnInit, Output, EventEmitter } from '@angular/core'
import { MatBottomSheetRef } from '@angular/material/bottom-sheet'
import { Router } from '@angular/router'

import { SessionService } from 'src/app/services/session.service'

@Component({
  selector: 'app-account-sheet',
  templateUrl: './account-sheet.component.html',
  styleUrls: ['./account-sheet.component.css'],
})
export class AccountSheetComponent implements OnInit {
  //@Output() closeSheetEvent = new EventEmitter();

  constructor(
    private bottomSheetRef: MatBottomSheetRef<AccountSheetComponent>,
    private router: Router,
    private sessionService: SessionService
  ) {}

  ngOnInit() {}

  logout(event: MouseEvent): void {
    this.sessionService.setCurrentCustomer(null)
    this.sessionService.setCustomerIsLogin(null)

    this.bottomSheetRef.dismiss()
    event.preventDefault()
    this.router.navigate(['/index'])
  }

  viewAccount(event: MouseEvent): void {
    //this.closeSheetEvent.emit //goes to parent (main component)
    this.bottomSheetRef.dismiss()
    event.preventDefault()
    this.router.navigate(['main/account'])
  }

}
