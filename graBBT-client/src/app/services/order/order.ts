import { OrderLineItem } from './order-line-item'
import { Customer } from '../customer/customer'
import { Outlet } from '../listing/outlet'


/*
    CURRENTLY NOT IN USE
*/


export class Order {

    orderId: number
    totalLineItem: number
    totalQuantity: number
    totalAmount: number
    transactionDateTime: Date 
    orderLineItems: OrderLineItem[]
    cancelled: boolean
    address: string
    addressDetails: string
    deliveryNote: string
    ccNum: string
    outletName: string

    constructor(
        orderId?: number,
        totalLineItem?: number,
        totalQuantity?: number,
        totalAmount?: number,
        transactionDateTime?: Date,
        orderLineItems?: OrderLineItem[],
        cancelled?: boolean,
        address?: string,
        addressDetails?: string,
        deliveryNote?: string,
        ccNum?: string,
        outletName?: string,
    ) {
        this.orderId = orderId
        this.totalLineItem = totalLineItem
        this.totalQuantity = totalQuantity
        this.totalAmount = totalAmount
        this.transactionDateTime = transactionDateTime
        this.orderLineItems = orderLineItems
        this.cancelled = cancelled
        this.address = address
        this.addressDetails = addressDetails
        this.deliveryNote = deliveryNote
        this.ccNum = ccNum
        this.outletName = outletName
    }
}
