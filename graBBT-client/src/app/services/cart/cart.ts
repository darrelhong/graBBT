import { Outlet } from '../listing/outlet'
import { CartItem } from './cart-item'
import { Promo } from '../promo/promo'

export class Cart {
  outlet: Outlet
  totalLineItem: number
  totalQuantity: number
  totalAmount: number
  cartItems: CartItem[]
  promo: Promo
}
