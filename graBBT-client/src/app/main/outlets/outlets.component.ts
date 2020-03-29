import { Component, OnInit } from '@angular/core'
import { Outlet } from 'src/app/services/listing/outlet'
import { ListingService } from 'src/app/services/listing/listing.service'
import { compileBaseDefFromMetadata } from '@angular/compiler'

@Component({
  selector: 'app-outlets',
  templateUrl: './outlets.component.html',
  styleUrls: ['./outlets.component.css'],
})
export class OutletsComponent implements OnInit {
  outlets: Outlet[]

  constructor(private listingService: ListingService) {}

  ngOnInit() {
    this.listingService.retrieveAllOutlets().subscribe(
      resp => {
        this.outlets = resp.outletEntites
        const date = new Date()
        const hour = date.getHours()
        let comp = true
        for (let o of this.outlets) {
          o.open = o.openingHour < hour && hour < o.closingHour ? true : false
        }
        this.outlets.sort((a, b) => {
          // show open outlets first
          return (+b.open) - (+a.open)
        })
        console.log(this.outlets)
      },
      error => {
        console.log(error)
      }
    )
  }
}
