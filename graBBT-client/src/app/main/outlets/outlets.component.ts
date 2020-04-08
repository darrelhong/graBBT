import { Component, OnInit } from '@angular/core'
import { Outlet } from 'src/app/services/listing/outlet'
import { ListingService } from 'src/app/services/listing/listing.service'
import { compileBaseDefFromMetadata } from '@angular/compiler'
import { Router } from '@angular/router'
import { MatSelectChange } from '@angular/material'

@Component({
  selector: 'app-outlets',
  templateUrl: './outlets.component.html',
  styleUrls: ['./outlets.component.css'],
})
export class OutletsComponent implements OnInit {
  outlets: Outlet[]
  lat: number
  lng: number
  constructor(private listingService: ListingService, private router: Router) {}

  ngOnInit() {
    this.listingService.retrieveAllOutlets().subscribe(
      resp => {
        this.outlets = resp.outletEntites
        const date = new Date()
        const hour = date.getHours()
        for (const o of this.outlets) {
          o.open = o.openingHour < hour && hour < o.closingHour ? true : false
        }
        this.outlets.sort((a, b) => {
          // show open outlets first
          return +b.open - +a.open
        })
        console.log(this.outlets)

        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(position =>
            this.success(position)
          )
        }
      },
      error => {
        console.log(error)
      }
    )
  }

  success(position) {
    this.lat = position.coords.latitude
    this.lng = position.coords.longitude
    console.log(`longitude: ${this.lng} | latitude: ${this.lat}`)
    this.outlets.forEach(o => {
      o.distance = this.distance(
        o.locationLatitude,
        o.locationLongitude,
        this.lat,
        this.lng
      )
      console.log(o.outletName + ' ' + o.distance)
    })
  }

  sort(event: MatSelectChange) {
    console.log('sort by: ' + event.value)
    const sortBy = event.value

    if (sortBy === 'name') {
      this.outlets.sort((a, b) => {
        if (+a.open < +b.open) {
          return 1
        }
        if (+a.open > +b.open) {
          return -1
        }
        if (a.outletName > b.outletName) {
          return 1
        }
        if (a.outletName < b.outletName) {
          return -1
        }
        return 0
      })
    } else if (sortBy === 'rating') {
      this.outlets.sort((a, b) => {
        if (+a.open < +b.open) {
          return 1
        }
        if (+a.open > +b.open) {
          return -1
        }
        if (a.outletRating < b.outletRating) {
          return 1
        }
        if (a.outletRating > b.outletRating) {
          return -1
        }
        return 0
      })
    } else if (sortBy === 'distance') {
      this.outlets.sort((a, b) => {
        if (+a.open < +b.open) {
          return 1
        }
        if (+a.open > +b.open) {
          return -1
        }
        if (a.distance > b.distance) {
          return 1
        }
        if (a.distance < b.distance) {
          return -1
        }
        return 0
      })
    }
  }

  distance(lat1, lon1, lat2, lon2) {
    if (lat1 === lat2 && lon1 === lon2) {
      return 0
    } else {
      const radlat1 = (Math.PI * lat1) / 180
      const radlat2 = (Math.PI * lat2) / 180
      const theta = lon1 - lon2
      const radtheta = (Math.PI * theta) / 180
      let dist =
        Math.sin(radlat1) * Math.sin(radlat2) +
        Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta)
      if (dist > 1) {
        dist = 1
      }
      dist = Math.acos(dist)
      dist = (dist * 180) / Math.PI
      dist = dist * 60 * 1.1515
      return dist
    }
  }
}
