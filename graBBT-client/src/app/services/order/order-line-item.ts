import { Listing } from '../listing/listing'

export class OrderLineItem {
    
    orderLineItemId: number
    listing: Listing
    quantity: number
    subTotal: number
    itemOptions: string[]
}
