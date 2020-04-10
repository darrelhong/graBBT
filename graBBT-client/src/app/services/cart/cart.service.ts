import { Injectable } from '@angular/core'
import { Cart } from './cart'
import { Outlet } from '../listing/outlet'
import { CartItem } from './cart-item'
import { Listing } from '../listing/listing'

import { BehaviorSubject } from 'rxjs'

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cart: BehaviorSubject<Cart>

  constructor() {
    if (sessionStorage.cart) {
      const tempCart: Cart = JSON.parse(sessionStorage.cart)
      if (tempCart.cartItems.length > 0) {
        this.cart = new BehaviorSubject<Cart>(JSON.parse(sessionStorage.cart))
        console.log(this.cart.value)
      } else {
        const newCart: Cart = new Cart()
        newCart.cartItems = []
        this.cart = new BehaviorSubject<Cart>(newCart)
      }
    } else {
      const newCart: Cart = new Cart()
      newCart.cartItems = []
      this.cart = new BehaviorSubject<Cart>(newCart)
    }
    sessionStorage.cart = JSON.stringify(this.cart.value)
    console.log(this.cart.getValue())
  }

  updateCart(newCart: Cart) {
    console.log('update cart caklled')
    if (newCart.cartItems.length > 0) {
      newCart = this.calculateCart(newCart)
      this.cart.next(newCart)
    } else {
      const cart = new Cart()
      cart.cartItems = []
      this.cart.next(cart)
    }
    sessionStorage.cart = JSON.stringify(this.cart.value)
  }

  newCart(item: CartItem, newOutlet: Outlet) {
    const newCart: Cart = {
      outlet: newOutlet,
      totalLineItem: null,
      totalQuantity: null,
      totalAmount: null,
      cartItems: [item],
    }
    this.updateCart(newCart)
  }

  calculateCart(cart: Cart): Cart {
    let totalLineItem = 0
    let totalQuantity = 0
    let totalAmount = 0
    cart.cartItems.forEach(item => {
      totalLineItem++
      totalQuantity += item.qty
      totalAmount += item.subtotal
    })
    cart.totalLineItem = totalLineItem
    cart.totalQuantity = totalQuantity
    cart.totalAmount = totalAmount
    return cart
  }

  // testing only
  changeCartDetails() {
    let cart: Cart = {
      outlet: new Outlet(
        1,
        'Test 2 Outlet',
        1.35069,
        103.872293,
        4.5,
        10,
        22,
        'gongcha.jpeg'
      ),
      totalLineItem: null,
      totalQuantity: null,
      totalAmount: null,
      cartItems: [
        {
          listing: {
            basePrice: 2.7,
            description: 'desc2',
            iceOptions: {
              'Normal Ice': 0,
              'No Ice': 0,
            },
            imageSrc: 'koigoldenoolongtea.jpeg',
            listingId: 4,
            name: 'Golden Oolong Tea',
            sizeOptions: {
              Medium: 0,
              Large: 1.1,
            },
            sugarOptions: {
              '50%': 0,
              '25%': 0,
              '0%': 0,
              '100%': 0,
            },
            toppingOptions: {
              'Aloe Vera': 1.2,
              'Golden Bubble': 0.6,
              'Konjac Jelly': 1.2,
            },
          },
          selectedOptions: [
            'Medium',
            '100% Sugar',
            'Less Ice',
            'Test',
            'Aloe Vera',
          ],
          qty: 2,
          subtotal: 7.8,
        },
      ],
    }
    cart = this.calculateCart(cart)
    this.cart.next(cart)
  }

  // testing only
  returnTestData() {
    let testCart: Cart = {
      outlet: new Outlet(
        1,
        'Test Outlet',
        1.35069,
        103.872293,
        4.5,
        10,
        22,
        'gongcha.jpeg'
      ),
      totalAmount: null,
      totalLineItem: null,
      totalQuantity: null,
      cartItems: [
        {
          listing: {
            basePrice: 3.3,
            description: 'desc',
            iceOptions: {
              'Normal Ice': 0,
              'No Ice': 0,
            },
            imageSrc: 'koiblackteamacchiato.jpeg',
            listingId: 3,
            name: 'Black Tea Macchiato',
            sizeOptions: {
              Medium: 0,
              Large: 1.1,
            },
            sugarOptions: {
              '50%': 0,
              '25%': 0,
              '0%': 0,
              '100%': 0,
            },
            toppingOptions: {
              'Aloe Vera': 1.2,
              'Golden Bubble': 0.6,
              'Konjac Jelly': 1.2,
            },
          },
          selectedOptions: [
            'Large',
            '25% Less Sugar',
            'Normal Ice',
            'Golden Bubble',
            'Test',
          ],
          qty: 1,
          subtotal: 5.0,
        },
        {
          listing: {
            basePrice: 3.3,
            description: 'desc',
            iceOptions: {
              'Normal Ice': 0,
              'No Ice': 0,
            },
            imageSrc: 'koiblackteamacchiato.jpeg',
            listingId: 3,
            name: 'Black Tea Macchiato',
            sizeOptions: {
              Medium: 0,
              Large: 1.1,
            },
            sugarOptions: {
              '50%': 0,
              '25%': 0,
              '0%': 0,
              '100%': 0,
            },
            toppingOptions: {
              'Aloe Vera': 1.2,
              'Golden Bubble': 0.6,
              'Konjac Jelly': 1.2,
            },
          },
          selectedOptions: [
            'Large',
            '25% Less Sugar',
            'Normal Ice',
            'Golden Bubble',
            'Test',
          ],
          qty: 1,
          subtotal: 5.0,
        },
      ],
    }
    return (testCart = this.calculateCart(testCart))
  }
}
