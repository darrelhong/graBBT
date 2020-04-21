import { Injectable } from '@angular/core'
import { Observable, throwError } from 'rxjs'
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http'
import { catchError } from 'rxjs/operators'

@Injectable({
  providedIn: 'root',
})
export class PromoService {
  baseUrl = '/api/Promo'

  constructor(private httpClient: HttpClient) {}

  retrieveAllPromos(): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + '/retrieveAllPromos')
      .pipe(catchError(this.handleError))
  }

  retrievePromosInCustomerWallet(customerId: number): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + '/retrievePromosByCustomerId/' + customerId)
      .pipe(catchError(this.handleError))
  }

  claimPromoIntoWallet(customerId: number, promoId: number): Observable<any> {
    return this.httpClient
      .get<any>(
        this.baseUrl + '/claimPromoIntoWallet/' + customerId + '' + promoId
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
