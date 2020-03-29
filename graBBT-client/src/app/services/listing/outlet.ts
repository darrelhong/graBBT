export class Outlet {
  outletId: number
  outletName: string
  locationLatitude: number
  locationLongitude: number
  outletRating: number
  openingHour: number
  closingHour: number
  open: boolean

  constructor(
    outletId?: number,
    outletName?: string,
    locationLatitude?: number,
    locationLongitude?: number,
    outletRating?: number,
    openingHour?: number,
    closingHour?: number
  ) {
    this.outletId = outletId
    this.outletName = outletName
    this.locationLatitude = locationLatitude
    this.locationLongitude = locationLongitude
    this.outletRating = outletRating
    this.openingHour = openingHour
    this.closingHour = closingHour
  }
}
