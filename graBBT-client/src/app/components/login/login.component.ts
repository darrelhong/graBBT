import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { SessionService } from 'src/app/services/session.service'
import { CustomerService } from 'src/app/services/customer/customer.service'
import { Customer } from 'src/app/services/customer/customer'
import { Router } from '@angular/router'
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm = this.formBuilder.group({
    username: [null, Validators.required],
    password: [null, Validators.required],
  })

  hide = true

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private sessionService: SessionService,
    private customerService: CustomerService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {}

  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username').value
      const password = this.loginForm.get('password').value
      console.log(username)
      console.log(password)
      this.sessionService.setUsername(username)
      this.sessionService.setPassword(password)

      this.customerService.customerLogin(username, password).subscribe(
        resp => {
          const customer: Customer = resp.customer
          console.log(customer)
          if (customer != null) {
            this.sessionService.setCustomerIsLogin(true)
            this.sessionService.setCurrentCustomer(customer)
          }

          this.router.navigate(['/main'])
        },
        error => {
          console.log(error)
          this.displayErrorSnackBar('An error occured.')
        }
      )
    }
  }

  displayErrorSnackBar(message: string) {
    this.snackBar.open(message, 'Dismiss', { duration: 5000 })
  }
}
