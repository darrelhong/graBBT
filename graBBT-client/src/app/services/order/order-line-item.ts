import { Listing } from '../listing/listing'

/*
    CURRENTLY NOT IN USE
*/

export class OrderLineItem {
    
    orderLineItemId: number
    listing: Listing
    quantity: number
    subTotal: number
    itemOptions: string[]
}
