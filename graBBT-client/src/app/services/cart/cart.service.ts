import { Injectable, ɵConsole } from '@angular/core'
import { BehaviorSubject, throwError, Observable } from 'rxjs'
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http'
import { catchError } from 'rxjs/operators'

import { Cart } from './cart'
import { Outlet } from '../listing/outlet'
import { CartItem } from './cart-item'
import { SessionService } from '../session.service'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
}

@Injectable({
  providedIn: 'root',
})
export class CartService {
  baseUrl = '/api/Customer'
  cart: BehaviorSubject<Cart>

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {
    if (sessionStorage.cart) {
      const tempCart: Cart = JSON.parse(sessionStorage.cart)
      if (tempCart.cartItems.length > 0) {
        // if cart exists and has items
        this.cart = new BehaviorSubject<Cart>(JSON.parse(sessionStorage.cart))
        console.log('exiting cart')
        console.log(this.cart.value)
      } else {
        // if cart exists but has no item
        const newCart: Cart = new Cart()
        newCart.cartItems = []
        this.cart = new BehaviorSubject<Cart>(newCart)
        console.log('new cart')
        console.log(this.cart.value)
      }
    } else {
      // if cart does not exist
      const newCart: Cart = new Cart()
      newCart.cartItems = []
      this.cart = new BehaviorSubject<Cart>(newCart)
      console.log('new cart')
      console.log(this.cart.value)
    }
    sessionStorage.cart = JSON.stringify(this.cart.value)
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

  clearCart() {
    const newCart: Cart = new Cart()
    newCart.cartItems = []
    this.updateCart(newCart)
  }

  checkout(
    address: string,
    addressDetails: string,
    ccNum: string,
    deliveryNote?: string
  ): Observable<any> {
    console.log('cartService.checkout() called')
    console.log(
      `address: ${address}, addressDetails: ${addressDetails}, ccNum: ${ccNum}, deliveryNote: ${deliveryNote}`
    )

    const currentCart = this.cart.value

    const checkoutItems = []
    for (const item of currentCart.cartItems) {
      checkoutItems.push({
        listingId: item.listing.listingId,
        selectedOptions: item.selectedOptions,
        qty: item.qty,
        subtotal: item.subtotal,
      })
    }

    const customerId = this.sessionService.getCurrentCustomer().customerId

    const checkoutReq = {
      customerId,
      outletId: currentCart.outlet.outletId,
      totalLineItem: currentCart.totalLineItem,
      totalQuantity: currentCart.totalQuantity,
      totalAmount: currentCart.totalAmount,
      checkoutItems,
      address,
      addressDetails,
      ccNum,
      deliveryNote,
    }
    console.log(checkoutReq)
    return this.httpClient
      .post<any>(this.baseUrl + '/checkout', checkoutReq, httpOptions)
      .pipe(catchError(this.handleError))
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
