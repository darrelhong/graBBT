export class Listing {
  listingId: number
  name: string
  basePrice: number
  description: string
  imageSrc: string

  constructor(
    listingId?: number,
    name?: string,
    basePrice?: number,
    description?: string,
    imageSrc?: string
  ) {
    this.listingId = listingId
    this.name = name
    this.basePrice = basePrice
    this.description = description
    this.imageSrc = imageSrc
  }
}
