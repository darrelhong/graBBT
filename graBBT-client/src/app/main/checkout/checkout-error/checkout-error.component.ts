import { Component, OnInit } from '@angular/core'
import { Router } from '@angular/router'

@Component({
  selector: 'app-checkout-error',
  templateUrl: './checkout-error.component.html',
  styleUrls: ['./checkout-error.component.css'],
})
export class CheckoutErrorComponent implements OnInit {
  errorMessage: string
  constructor(private router: Router) {}

  ngOnInit() {
    if (window.history.state.error != null) {
      this.errorMessage = window.history.state.error
    } else {
      this.router.navigate(['/error'], {
        state: {
          error: 'Checkout Error: no data passed in',
        },
      })
    }
  }
}
