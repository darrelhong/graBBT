import { Component, OnInit } from '@angular/core'
import { CartService } from 'src/app/services/cart/cart.service'
import { Cart } from 'src/app/services/cart/cart'
import { FormBuilder, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { map, catchError } from 'rxjs/operators'
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { throwError } from 'rxjs'
import { CustomerService } from 'src/app/services/customer/customer.service'

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  showCheckout: boolean

  options: string[]

  cart: Cart
  checkoutForm = this.fb.group({
    address: ['', Validators.required],
    addressDetails: ['', Validators.required],
    deliveryNote: [''],
    ccNum: [
      '',
      Validators.compose([
        Validators.required,
        // chech if value is 16 digits optionally dash separated
        Validators.pattern(/^\d{4}-?\d{4}-?\d{4}-?\d{4}$/),
      ]),
    ],
  })

  constructor(
    private cartService: CartService,
    private customerService: CustomerService,
    private fb: FormBuilder,
    private router: Router,
    private httpClient: HttpClient
  ) {}

  ngOnInit() {
    this.showCheckout = this.router.url === '/main/checkout'

    this.cartService.cart.subscribe(cart => {
      this.cart = cart
    })

    this.checkoutForm.get('address').valueChanges.subscribe(value => {
      if (value.length > 0) {
        this.httpClient
          .get<any>(
            `/gmapsapi/place/autocomplete/json?input=${value}&types=address&components=country:sg&key=AIzaSyCUL4E76ECHB6KNyro4s7psZw44hensP70`
          )
          .pipe(catchError(this.handleError))
          .subscribe(resp => {
            this.options = resp.predictions.map(
              x => x.structured_formatting.main_text
            )
            console.log(this.options)
          })
      }
      console.log(value)
    })
  }

  checkout() {
    const address = this.checkoutForm.get('address').value
    const addressDetails = this.checkoutForm.get('addressDetails').value
    const ccNum = this.checkoutForm.get('ccNum').value
    const deliveryNote = this.checkoutForm.get('deliveryNote').value
    this.cartService
      .checkout(address, addressDetails, ccNum, deliveryNote)
      .subscribe(
        resp => {
          console.log(resp)
          this.showCheckout = false
          this.cartService.clearCart()

          this.customerService.refreshCustomer() //to update the customer in the model (reflect new bb points)
          this.router.navigate(['/main/checkout/success'], {
            state: {
              orderEntity: resp,
            },
          })
        },
        error => {
          console.log(error)
          this.showCheckout = false
          this.router.navigate(['main/checkout/error'], {
            state: {
              error,
            },
          })
        }
      )
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = ''

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error.message
    } else {
      errorMessage =
        'A HTTP error has occurred: ' +
        `HTTP ${error.status}: ${error.error.message}`
    }

    console.error(errorMessage)
    return throwError(errorMessage)
  }
}
