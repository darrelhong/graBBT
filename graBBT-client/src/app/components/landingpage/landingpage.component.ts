import { Component, OnInit, Inject } from '@angular/core'
import { Observable } from 'rxjs'
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout'
import { map, shareReplay } from 'rxjs/operators'
import { SessionService } from 'src/app/services/session.service'
import { Router } from '@angular/router'
import { MatBottomSheet } from '@angular/material/bottom-sheet'
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog'

import { AccountSheetComponent } from '../account-sheet/account-sheet.component'
import { Listing } from 'src/app/services/listing/listing'
import { ListingService } from 'src/app/services/listing/listing.service'
import { FormBuilder, FormArray, FormControl, Validators } from '@angular/forms'

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
  listing: any
  listings: any[]

  constructor(
    private breakpointObserver: BreakpointObserver,
    private sessionService: SessionService,
    private router: Router,
    private bottomSheet: MatBottomSheet,
    private dialog: MatDialog,
    private lsitingService: ListingService
  ) {}

  ngOnInit() {
    this.lsitingService.retrieveListingsByOutletId('1').subscribe(resp => {
      console.log(resp)
      this.listings = resp.listings
      this.listing = this.listings[0]
      console.log(this.listing)
    })

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

  openDialog(): void {
    const dialogRef = this.dialog.open(ItemDialogComponent, {
      width: '500px',
      data: this.listing,
    })

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed')
    })
  }
}

@Component({
  selector: 'app-item-dialog',
  templateUrl: 'item-dialog.component.html',
  styleUrls: ['./landingpage.component.css'],
})
export class ItemDialogComponent {
  orderForm = this.fb.group({
    sizeChoice: ['', Validators.required],
    sugarChoice: ['', Validators.required],
    iceChoice: ['', Validators.required],
    toppingFormArray: this.fb.array([]),
    quantity: [0, Validators.min(1)],
  })

  toppingArr = []
  get toppingFormArray() {
    return this.orderForm.get('toppingFormArray') as FormArray
  }

  constructor(
    public dialogRef: MatDialogRef<ItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.toppingArr = Object.entries(this.data.toppingOptions)
    console.log(this.toppingArr)
    this.toppingArr.forEach((o, i) => {
      this.toppingFormArray.push(this.fb.control(false))
    })
  }

  onNoClick(): void {
    this.dialogRef.close()
  }

  decrementQty() {
    if (this.orderForm.get('quantity').value > 0) {
      this.orderForm
        .get('quantity')
        .setValue(this.orderForm.get('quantity').value - 1)
    }
  }

  incrementQty() {
    this.orderForm
      .get('quantity')
      .setValue(this.orderForm.get('quantity').value + 1)
  }
}
