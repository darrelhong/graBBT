import { Injectable } from '@angular/core'
import { Customer } from './customer/customer'

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  constructor() {}

  getCustomerIsLogIn(): boolean {
    if (sessionStorage.isLogin == 'true') {
      return true
    } else {
      return false
    }
  }

  setCustomerIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin
  }

  setUsername(username: string): void {
    sessionStorage.username = username
  }

  getUsername(): string {
    return sessionStorage.username
  }

  setPassword(password: string): void {
    sessionStorage.password = password
  }

  getPassword(): string {
    return sessionStorage.password
  }

  setCurrentCustomer(currentCustomer: Customer): void {
    sessionStorage.currentCustomer = JSON.stringify(currentCustomer)
  }

  getCurrentCustomer(): Customer {
    return JSON.parse(sessionStorage.currentCustomer)
  }
}
