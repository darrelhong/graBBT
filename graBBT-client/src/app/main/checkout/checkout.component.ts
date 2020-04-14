import { Component, OnInit } from '@angular/core'
import { CartService } from 'src/app/services/cart/cart.service'
import { Cart } from 'src/app/services/cart/cart'
import { FormBuilder, Validators } from '@angular/forms'
import { Router } from '@angular/router'

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  showCheckout: boolean

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
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    console.log(this.router.url === '/main/checkout')
    this.showCheckout = this.router.url === '/main/checkout'

    this.cartService.cart.subscribe(cart => {
      this.cart = cart
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
}
