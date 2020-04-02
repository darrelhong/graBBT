import { Injectable } from '@angular/core'
import { Observable, throwError } from 'rxjs'
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http'
import { catchError } from 'rxjs/operators'

import { Customer } from './customer'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
}

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  baseUrl = '/api/Customer'

  constructor(private httpClient: HttpClient) {}

  customerLogin(username: string, password: string): Observable<any> {
    return this.httpClient
      .get<any>(
        this.baseUrl +
          '/customerLogin?username=' +
          username +
          '&password=' +
          password
      )
      .pipe(catchError(this.handleError))

    // if (username === 'customer' && password === 'password') {
    //   return of({
    //     customer: JSON.stringify(
    //       new Customer(
    //         1,
    //         'Customer 1',
    //         'customer',
    //         'password',
    //         '87654321',
    //         'Addeess 1',
    //         'email@gmail.com',
    //         0
    //       )
    //     ),
    //   })
    // } else {
    //   return throwError('error')
    // }
  }

  signUp(newCustomer: Customer): Observable<any> {
    const createCustReq = {
      newCustomer: newCustomer,
    }
    // send post request
    return this.httpClient
      .post<any>(this.baseUrl + '/createNewCustomer', createCustReq, httpOptions)
      .pipe(catchError(this.handleError))

  }

  updateCustomer(updatedCustomer: Customer): Observable<any> {
    const custUpdateReq = {
      "updatedCustomer": updatedCustomer,
    }

    return this.httpClient
      .post<any>(this.baseUrl + '/updateCustomer', custUpdateReq, httpOptions)
      .pipe(catchError(this.handleError))
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = ''

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error.message
    } else {
      errorMessage =
        'A HTTP error has occurred: ' +
        `HTTP ${error.status}: ${error.error.message}`
    }

    console.error(errorMessage)
    return throwError(errorMessage)
  }
}
