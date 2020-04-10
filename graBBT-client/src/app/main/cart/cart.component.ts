import { Component, OnInit } from '@angular/core'
import { CartService } from 'src/app/services/cart/cart.service'
import { Cart } from 'src/app/services/cart/cart'

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cart: Cart
  constructor(private cartService: CartService) {}

  ngOnInit() {
    this.cartService.cart.subscribe(cart => {
      this.cart = cart
    })
  }

  removeItem(i: number) {
    this.cart.cartItems.splice(i, 1)
    this.cartService.updateCart(this.cart)
  }

  isEmpty() {
    return this.cart.cartItems.length <= 0
  }

  checkout() {
    alert('NOT DONE YET')
  }
  // testing only
  test() {
    this.cartService.changeCartDetails()
  }
}
