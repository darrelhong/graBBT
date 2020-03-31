import { Component, OnInit } from '@angular/core'
import { ParamMap, ActivatedRoute } from '@angular/router'

import { Listing } from 'src/app/services/listing/listing'
import { ListingService } from 'src/app/services/listing/listing.service'
import { switchMap } from 'rxjs/operators'

@Component({
  selector: 'app-listings',
  templateUrl: './listings.component.html',
  styleUrls: ['./listings.component.css'],
})
export class ListingsComponent implements OnInit {
  listings: Listing[]

  constructor(
    private listingService: ListingService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const temp = this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        return this.listingService.retrieveListingsByOutletId(params.get('id'))
      })
    )

    temp.subscribe(resp => {
      this.listings = resp.listings
      console.log(this.listings)
    })
  }
}
