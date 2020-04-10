import { Listing } from '../listing/listing'

export interface CartItem {
  listing: Listing
  selectedOptions: string[]
  qty: number
  subtotal: number
}
