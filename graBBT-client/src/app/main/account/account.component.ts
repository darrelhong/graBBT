import { Component, OnInit } from '@angular/core'
import { SessionService } from 'src/app/services/session.service'
import { Customer } from 'src/app/services/customer/customer'
import { FormBuilder, Validators, NgForm } from '@angular/forms'
import { CustomerService } from 'src/app/services/customer/customer.service'
import { Router } from '@angular/router'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Order } from 'src/app/services/order/order'
import { Promo } from 'src/app/services/promo/promo'
import { PromoService } from 'src/app/services/promo/promo.service'
import { SidenavService } from 'src/app/services/sidenav.service'

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
})
export class AccountComponent implements OnInit {
  //For Account Details Tab
  currentCustomer: Customer
  updateCustomer: Customer
  editView: boolean

  resultSuccess: boolean
  resultError: boolean
  message: string

  //For Wallet Tab
  walletPromos: Promo[]
  usedStatus: Boolean[]

  //For Orders Tab
  orders: Order[]
  outletNames: string[]
  dates: string[]

  updateAccountForm = this.formBuilder.group({
    name: [
      null,
      Validators.compose([Validators.required, Validators.maxLength(64)]),
    ],
    email: [null, Validators.required],
    phoneNumber: [
      null,
      Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]*$'),
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
    ],
    address: [null, Validators.required],
  })

  constructor(
    private sessionService: SessionService,
    private customerService: CustomerService,
    private promoService: PromoService,
    private formBuilder: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private sidenavService: SidenavService
  ) {
    this.editView = false
    this.resultSuccess = false
    this.resultError = false
    this.updateCustomer = new Customer()
  }

  ngOnInit() {
    //For account details tab
    this.currentCustomer = this.sessionService.getCurrentCustomer()
    console.log(this.currentCustomer.password)
    this.updateCustomer.customerId = this.currentCustomer.customerId
    this.updateCustomer.username = this.currentCustomer.username
    this.updateCustomer.password = this.currentCustomer.password
    this.updateCustomer.bbPoints = this.currentCustomer.bbPoints
    this.updateCustomer.name = this.currentCustomer.name
    this.updateCustomer.phoneNumber = this.currentCustomer.phoneNumber
    this.updateCustomer.email = this.currentCustomer.email
    this.updateCustomer.address = this.currentCustomer.address

    //For wallet tab
    this.promoService
      .retrievePromosInCustomerWallet(this.currentCustomer.customerId)
      .subscribe(
        resp => {
          this.walletPromos = resp.customerCurrentPromos
          this.usedStatus = resp.usedStatus
        },
        error => {
          this.displaySnackBar(
            'An error ocurred while retrieving customer current promos'
          )
        }
      )

    //For orders tab
    this.customerService.retrieveOrders().subscribe(
      resp => {
        this.orders = resp.orders
        this.dates = resp.dates
        this.outletNames = resp.outletNames

        console.log(this.dates)
        for (var i = 0; i < this.dates.length; i++) {
          this.dates[i] = this.dates[i].slice(0, -5)
        }

        console.log(this.orders)
        console.log(this.dates)
        console.log(this.outletNames)
      },
      error => {
        console.log('error in retrieving orders: ' + error)
      }
    )
  }

  useMe(): void {
    this.sidenavService.open()
    this.displaySnackBar("Let's add/checkout your items first!")
  }

  updateAccount(updateAccountForm: NgForm) {
    console.log('update account form')

    if (updateAccountForm.valid) {
      this.customerService.updateCustomer(this.updateCustomer).subscribe(
        resp => {
          this.resultSuccess = true
          this.resultError = false

          this.sessionService.setCurrentCustomer(this.updateCustomer) //kiv

          this.displaySnackBar('Customer details updated successfully')
          this.editView = false
          this.router.navigate(['/main/account']).then(() => {
            window.location.reload()
          })
        },
        error => {
          this.resultError = true
          this.resultSuccess = false
          this.displaySnackBar(
            'An error occured while updating customer details'
          )

          console.log('********** account.component.ts: ' + error)
        }
      )
    } else {
      this.displaySnackBar('One or more fields is invalid. Please try again!')
    }
  }

  displaySnackBar(message: string) {
    this.snackBar.open(message, 'Dismiss', { duration: 5000 })
  }

  clear() {
    this.updateCustomer.customerId = this.currentCustomer.customerId
    this.updateCustomer.username = this.currentCustomer.username
    this.updateCustomer.password = this.currentCustomer.password
    this.updateCustomer.bbPoints = this.currentCustomer.bbPoints
    this.updateCustomer.name = this.currentCustomer.name
    this.updateCustomer.phoneNumber = this.currentCustomer.phoneNumber
    this.updateCustomer.email = this.currentCustomer.email
    this.updateCustomer.address = this.currentCustomer.address
  }

  cancel() {
    //reset back
    this.clear()
    this.editView = false
  }
}
