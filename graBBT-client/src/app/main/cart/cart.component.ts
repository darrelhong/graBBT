import { Component, OnInit } from '@angular/core'
import { CartService } from 'src/app/services/cart/cart.service'
import { Cart } from 'src/app/services/cart/cart'
import { Router } from '@angular/router'
import { SidenavService } from 'src/app/services/sidenav.service'

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cart: Cart
  constructor(
    private cartService: CartService,
    private router: Router,
    private sidenavService: SidenavService
  ) {}

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
    this.sidenavService.close()
    this.router.navigate(['main/checkout'])
  }
  // testing only
  test() {
    this.cartService.changeCartDetails()
  }
}
