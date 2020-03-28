export class Customer {
  customerId: number
  name: string
  username: string
  password: string
  phoneNumber: string
  address: string
  email: string
  bbPoints: number

  constructor(
    customerId?: number,
    name?: string,
    username?: string,
    password?: string,
    phoneNumber?: string,
    address?: string,
    email?: string,
    bbPoints?: number
  ) {
    this.customerId = customerId
    this.name = name
    this.username = username
    this.password = password
    this.phoneNumber = phoneNumber
    this.address = address
    this.email = email
    this.bbPoints = bbPoints
  }
}
