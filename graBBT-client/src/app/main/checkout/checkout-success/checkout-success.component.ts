import { Component, OnInit } from '@angular/core'
import { Router } from '@angular/router'

@Component({
  selector: 'app-checkout-success',
  templateUrl: './checkout-success.component.html',
  styleUrls: ['./checkout-success.component.css'],
})
export class CheckoutSuccessComponent implements OnInit {
  orderEntity: any

  constructor(private router: Router) {}

  ngOnInit() {
    // this.orderEntity = this.returnTestData()
    // this.orderEntity.transactionDateTime = this.orderEntity.transactionDateTime.slice(0, -3)
    if (window.history.state.orderEntity != null) {
      this.orderEntity = window.history.state.orderEntity
      this.orderEntity.transactionDateTime = this.orderEntity.transactionDateTime.slice(
        0,
        -5
      )
    } else {
      this.router.navigate(['/error'], {
        state: {
          error: 'Checkout Error: no data passed in',
        },
      })
    }
  }

  returnTestData() {
    return {
      address: 'College Ave E',
      addressDetails: 'UTR',
      cancelled: false,
      ccNum: '2167582376238476',
      deliveryNote: 'Meet me at the lobby',
      id: 11,
      orderLineItems: [
        {
          itemOptions: ['Large', '0%', 'No Ice'],
          listing: {
            basePrice: 3.3,
            imageSrc: 'koiblackteamacchiato.jpeg',
            listingId: 3,
            name: 'Black Tea Macchiato',
          },
          orderLineItemId: 11,
          quantity: 1,
          subTotal: 4.4,
        },
        {
          itemOptions: [
            'Large',
            '0%',
            'No Ice',
            'Aloe Vera',
            'Golden Bubble',
            'Konjac Jelly',
          ],
          listing: {
            basePrice: 2.7,
            imageSrc: 'koigoldenoolongtea.jpeg',
            listingId: 4,
            name: 'Golden Oolong Tea',
          },
          orderLineItemId: 12,
          quantity: 1,
          subTotal: 6.8,
        },
      ],
      outlet: {
        closingHour: 20,
        imageSrc: 'koi.jpeg',
        isActive: true,
        locationLatitude: 1.3329,
        locationLongitude: 103.7436,
        openingHour: 9,
        outletId: 2,
        outletName: 'KOI Jurong',
        outletRating: 5,
        outletRevenueDaily: 0,
        outletRevenueMonthly: 0,
        outletRevenueOverall: 0,
        ratingCount: 0,
      },
      totalAmount: 11.2,
      totalLineItem: 2,
      totalQuantity: 2,
      transactionDateTime: '2020-04-13T09:06:06.552Z[UTC]',
    }
  }
}
