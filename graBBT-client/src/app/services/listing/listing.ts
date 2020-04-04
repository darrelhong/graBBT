export class Listing {
  listingId: number
  name: string
  basePrice: number
  description: string
  imageSrc: string
  sizeOptions: { [name: string]: number }
  sugarOptions: { [name: string]: number }
  iceOptions: { [name: string]: number }
  toppingOptions: { [name: string]: number }

  constructor(
    listingId?: number,
    name?: string,
    basePrice?: number,
    description?: string,
    imageSrc?: string,
    sizeOptions?: { [name: string]: number },
    sugarOptions?: { [name: string]: number },
    iceOptions?: { [name: string]: number },
    toppingOptions?: { [name: string]: number }
  ) {
    this.listingId = listingId
    this.name = name
    this.basePrice = basePrice
    this.description = description
    this.imageSrc = imageSrc
    this.sizeOptions = sizeOptions
    this.sugarOptions = sugarOptions
    this.iceOptions = iceOptions
    this.toppingOptions = toppingOptions
  }
}
