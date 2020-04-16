import { Component, OnInit } from '@angular/core'
import {
  ParamMap,
  ActivatedRoute,
  Router,
  NavigationStart,
} from '@angular/router'

import { Listing } from 'src/app/services/listing/listing'
import { ListingService } from 'src/app/services/listing/listing.service'
import { switchMap, map, filter } from 'rxjs/operators'
import { DialogListingComponent } from './dialog-listing/dialog-listing.component'
import { MatDialog } from '@angular/material/dialog'
import { Outlet } from 'src/app/services/listing/outlet'
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser'
import { Cart } from 'src/app/services/cart/cart'
import { CartService } from 'src/app/services/cart/cart.service'
import { MatSelectChange } from '@angular/material'

@Component({
  selector: 'app-listings',
  templateUrl: './listings.component.html',
  styleUrls: ['./listings.component.css'],
})
export class ListingsComponent implements OnInit {
  listings: Listing[]
  filteredListings: Listing[]
  selectedListing: Listing
  outlet: Outlet
  mapSrc: SafeResourceUrl

  constructor(
    private listingService: ListingService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.listingService
        .retrieveOutletById(params.get('id'))
        .subscribe(resp => {
          this.outlet = resp
          this.setMapUrl()
        })
      this.listingService
        .retrieveListingsByOutletId(params.get('id'))
        .subscribe(resp => {
          this.listings = resp.listings
          this.filteredListings = resp.listings
          console.log(this.listings)
        })
    })
  }

  setMapUrl() {
    this.mapSrc = this.sanitizer.bypassSecurityTrustResourceUrl(
      'https://www.google.com/maps/embed/v1/place?key=AIzaSyCUL4E76ECHB6KNyro4s7psZw44hensP70&q=' +
        this.outlet.locationLatitude +
        ',' +
        this.outlet.locationLongitude +
        '&zoom=16'
    )
  }

  openDialog(index: number): void {
    this.selectedListing = this.listings[index]
    const dialogRef = this.dialog.open(DialogListingComponent, {
      width: '500px',
      data: { listing: this.selectedListing, outlet: this.outlet },
    })

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed')
    })
  }

  sort(event: MatSelectChange) {
    const sortBy = event.value

    if (sortBy === 'name') {
      this.listings.sort((a, b) => {
        if (a.name > b.name) {
          return 1
        } else if (a.name < b.name) {
          return -1
        } else {
          return 0
        }
      })
    }
  }

  filter(event) {
    // console.log(event.target.value)
    const term = event.target.value.toLowerCase()
    this.filteredListings = this.listings.filter(l => {
      return l.name.toLowerCase().includes(term)
    })
  }
}
