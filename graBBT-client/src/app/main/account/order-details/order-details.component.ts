import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CustomerService } from 'src/app/services/customer/customer.service'
import { CartService } from 'src/app/services/cart/cart.service'
import { MatDialog } from '@angular/material'
import { CancelDialogComponent } from 'src/app/components/cancel-dialog/cancel-dialog.component'

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit {
  orderEntity: any

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService,
    private cartService: CartService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    // testing
    // this.orderEntity = this.returnTestData()
    // this.orderEntity.transactionDateTime = this.orderEntity.transactionDateTime.slice(
    //   0,
    //   -5
    // )

    this.route.paramMap.subscribe(params => {
      this.customerService.retrieveOrderByOrderId(params.get('id')).subscribe(
        resp => {
          resp.transactionDateTime = resp.transactionDateTime.slice(0, -5)
          this.orderEntity = resp
          console.log(this.orderEntity)
        },
        error => {
          this.router.navigate(['/error'], {
            state: {
              error,
            },
          })
        }
      )
    })
  }

  cancelOrder() {
    this.cartService.cancelOrder(this.orderEntity.orderId).subscribe(
      resp => {
        console.log(resp)
        resp.transactionDateTime = resp.transactionDateTime.slice(0, -5)
        this.orderEntity = resp
      },
      error => {
        console.log(error)
        this.router.navigate(['main/checkout/error'], {
          state: {
            error,
          },
        })
      }
    )
  }

  cancelDialog(): void {
    const dialogRef = this.dialog.open(CancelDialogComponent, {
      width: '250px',
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cancelOrder()
      }
    })
  }

  // testing
  returnTestData() {
    return {
      address: 'College Ave E',
      addressDetails: 'UTR',
      cancelled: true,
      ccNum: '2167582376238476',
      deliveryNote: 'Meet me at the lobby',
      orderId: 11,
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
