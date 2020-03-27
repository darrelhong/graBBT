import { Component, OnInit } from '@angular/core'
import { MatBottomSheetRef } from '@angular/material/bottom-sheet'
import { Router } from '@angular/router'

import { SessionService } from 'src/app/services/session.service'
@Component({
  selector: 'app-account-sheet',
  templateUrl: './account-sheet.component.html',
  styleUrls: ['./account-sheet.component.css'],
})
export class AccountSheetComponent implements OnInit {
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
}
