import { Outlet } from '../listing/outlet'
import { CartItem } from './cart-item'

export class Cart {
  outlet: Outlet
  totalLineItem: number
  totalQuantity: number
  totalAmount: number
  cartItems: CartItem[]
}
