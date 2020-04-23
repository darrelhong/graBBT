import { Injectable } from '@angular/core'
import { Observable, throwError } from 'rxjs'
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http'
import { catchError } from 'rxjs/operators'

import { Customer } from './customer'
import { SessionService } from '../session.service'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
}

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  baseUrl = '/api/Customer'

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

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
  }

  signUp(newCustomer: Customer): Observable<any> {
    const createCustReq = {
      newCustomer: newCustomer,
    }
    // send post request
    return this.httpClient
      .post<any>(
        this.baseUrl + '/createNewCustomer',
        createCustReq,
        httpOptions
      )
      .pipe(catchError(this.handleError))
  }

  refreshCustomer(): void {
    const customerId = this.sessionService.getCurrentCustomer().customerId
    this.httpClient
      .get<any>(this.baseUrl + '/refreshCustomer?customerId=' + customerId)
      .pipe(catchError(this.handleError))
      .subscribe(
        resp => {
          this.sessionService.setCurrentCustomer(resp.refreshedCustomer)
        },
        error => {
          console.log('refreshCustomer() encountered an error')
        }
      )
  }

  updateCustomer(updatedCustomer: Customer): Observable<any> {
    const custUpdateReq = {
      updatedCustomer: updatedCustomer,
    }

    return this.httpClient
      .post<any>(this.baseUrl + '/updateCustomer', custUpdateReq, httpOptions)
      .pipe(catchError(this.handleError))
  }

  retrieveOrders(): Observable<any> {
    const customerId = this.sessionService.getCurrentCustomer().customerId

    return this.httpClient
      .get<any>(this.baseUrl + '/retrieveOrders?customerId=' + customerId)
      .pipe(catchError(this.handleError))
  }

  retrieveOrderByOrderId(orderId: string): Observable<any> {
    const customerId = this.sessionService.getCurrentCustomer().customerId
    return this.httpClient
      .get<any>(
        this.baseUrl +
          '/retrieveOrderByOrderId?orderId=' +
          orderId +
          '&customerId=' +
          customerId
      )
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
