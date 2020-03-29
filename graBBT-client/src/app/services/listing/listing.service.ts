import { Injectable } from '@angular/core'
import { Observable, throwError } from 'rxjs'
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http'
import { catchError } from 'rxjs/operators'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
}

@Injectable({
  providedIn: 'root',
})
export class ListingService {
  basUrl = '/api/Listing'

  constructor(private httpClient: HttpClient) {}

  retrieveAllOutlets(): Observable<any> {
    return this.httpClient
      .get<any>(this.basUrl + '/retrieveAllOutlets')
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
