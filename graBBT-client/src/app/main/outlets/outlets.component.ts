import { Component, OnInit } from '@angular/core'
import { Outlet } from 'src/app/services/listing/outlet'
import { ListingService } from 'src/app/services/listing/listing.service'
import { compileBaseDefFromMetadata } from '@angular/compiler'
import { Router } from '@angular/router'


@Component({
  selector: 'app-outlets',
  templateUrl: './outlets.component.html',
  styleUrls: ['./outlets.component.css'],
})
export class OutletsComponent implements OnInit {
  outlets: Outlet[]
  slides = [
    {
      url: '/images/carousel/test-carousel-image-1.jpg'
    },
    {
      url: '/images/carousel/test-carousel-image-2.jpeg'
    },
    {
      url:'/images/carousel/test-carousel-image-3.jpg'
    }
  ]

  constructor(private listingService: ListingService, private router: Router) {}

  ngOnInit() {
    //retrieve outlets
    this.listingService.retrieveAllOutlets().subscribe(
      resp => {
        this.outlets = resp.outletEntites
        const date = new Date()
        const hour = date.getHours()
        for (let o of this.outlets) {
          o.open = o.openingHour < hour && hour < o.closingHour ? true : false
        }
        this.outlets.sort((a, b) => {
          // show open outlets first
          return +b.open - +a.open
        })
        console.log(this.outlets)
      },
      error => {
        console.log(error)
      }
    )
  }
}
