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

@Component({
  selector: 'app-listings',
  templateUrl: './listings.component.html',
  styleUrls: ['./listings.component.css'],
})
export class ListingsComponent implements OnInit {
  listings: Listing[]
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
          this.mapUrl()
        })
      this.listingService
        .retrieveListingsByOutletId(params.get('id'))
        .subscribe(resp => {
          this.listings = resp.listings
        })
    })
  }

  mapUrl() {
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
      data: this.selectedListing,
    })

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed')
    })
  }
}
