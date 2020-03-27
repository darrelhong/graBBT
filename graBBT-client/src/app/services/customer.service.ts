import { Injectable } from '@angular/core'
import { Observable, of, throwError } from 'rxjs'
import { Customer } from './customer'

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor() {}

  customerLogin(username: string, password: string): Observable<any> {
    if (username === 'customer' && password === 'password') {
      return of({
        customer: JSON.stringify(
          new Customer(
            1,
            'Customer 1',
            'customer',
            'password',
            '87654321',
            'Addeess 1',
            'email@gmail.com',
            0
          )
        ),
      })
    } else {
      return throwError('error')
    }
  }

  signUp(newCustomer: Customer): Observable<any> {
    const createCustReq = {
      customer: newCustomer,
    }
    // send post request
    return of({
      createCustReq,
    })
  }
}
