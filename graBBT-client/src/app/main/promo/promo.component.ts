import { Component, OnInit } from '@angular/core'
import { Promo } from 'src/app/services/promo/promo'
import { PromoService } from 'src/app/services/promo/promo.service'
import { SessionService } from 'src/app/services/session.service'
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-promo',
  templateUrl: './promo.component.html',
  styleUrls: ['./promo.component.css'],
})
export class PromoComponent implements OnInit {
  promos: Promo[]
  customerCurrentPromos: Promo[]
  usedStatus: Boolean[]
  customerId: number

  constructor(
    private promoService: PromoService,
    private sessionService: SessionService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.promoService.retrieveAllPromos().subscribe(
      resp => {
        this.promos = resp.promos
        console.log(this.promos)
      },
      error => {
        this.displaySnackBar(
          'An error occured while retrieving available promos'
        )
      }
    )

    this.customerId = this.sessionService.getCurrentCustomer().customerId

    this.promoService.retrievePromosInCustomerWallet(this.customerId).subscribe(
      resp => {
        this.customerCurrentPromos = resp.customerCurrentPromos
        this.usedStatus = resp.usedStatus
      },
      error => {
        this.displaySnackBar(
          'An error ocurred while retrieving customer current promos'
        )
      }
    )
  }

  checkClaim(index: number): Boolean {
    let claimingPromo: Promo = this.promos[index]

    let alreadyClaimed = false

    for (var p of this.customerCurrentPromos) {
      if (p.promoId == claimingPromo.promoId) {
        alreadyClaimed = true
        break
      }
    }

    console.log(alreadyClaimed)
    return alreadyClaimed
  }

  claimPromo(index: number): void {
    let claimingPromo: Promo = this.promos[index]

    let alreadyClaimed = this.checkClaim(index)

    if (alreadyClaimed) {
      this.displaySnackBar('You already have this promo in your wallet!')
    } else {
      //can claim

      this.promoService
        .claimPromoIntoWallet(this.customerId, claimingPromo.promoId)
        .subscribe(
          resp => {
            this.customerCurrentPromos = resp.customerCurrentPromos //updates
            this.usedStatus = resp.usedStatus //updates
            this.displaySnackBar('Added to wallet!')
          },
          error => {
            this.displaySnackBar('An error ocurred while claiming promo')
          }
        )
    }
  }

  displaySnackBar(message: string) {
    this.snackBar.open(message, 'Dismiss', { duration: 5000 })
  }
}
