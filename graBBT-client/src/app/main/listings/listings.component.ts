import { Component, OnInit } from '@angular/core'
import { ParamMap, ActivatedRoute } from '@angular/router'

import { Listing } from 'src/app/services/listing/listing'
import { ListingService } from 'src/app/services/listing/listing.service'
import { switchMap } from 'rxjs/operators'
import { DialogListingComponent } from './dialog-listing/dialog-listing.component'
import { MatDialog } from '@angular/material/dialog'

@Component({
  selector: 'app-listings',
  templateUrl: './listings.component.html',
  styleUrls: ['./listings.component.css'],
})
export class ListingsComponent implements OnInit {
  listings: Listing[]
  selectedListing: Listing

  constructor(
    private listingService: ListingService,
    private route: ActivatedRoute,
    private dialog: MatDialog
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
      this.selectedListing = this.listings[0]
    })
  }

  openDialog(index: number): void {
    this.selectedListing = this.listings[index]
    const dialogRef = this.dialog.open(DialogListingComponent, {
      width: '500px',
      data: this.selectedListing,
    })

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed')
    })
  }
}
