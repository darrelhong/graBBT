import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'

import { Customer } from 'src/app/services/customer/customer'
import { CustomerService } from 'src/app/services/customer/customer.service'
import { SessionService } from 'src/app/services/session.service'
import { Router } from '@angular/router'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  hide = true

  signupForm = this.formBuilder.group({
    name: [
      null,
      Validators.compose([Validators.required, Validators.maxLength(64)]),
    ],
    username: [
      null,
      Validators.compose([
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(32),
      ]),
    ],
    password: [null, Validators.required],
    email: [null, Validators.compose([Validators.required, Validators.email])],
    phoneNumber: [
      null,
      Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]*$'),
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
    ],
    address: [
      null,
      Validators.compose([Validators.required, Validators.maxLength(64)]),
    ],
  })

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private customerService: CustomerService,
    private sessionService: SessionService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {}

  onSubmit() {
    console.log('submitted')

    const newCustomer = new Customer()
    newCustomer.username = this.signupForm.get('username').value
    newCustomer.password = this.signupForm.get('password').value
    newCustomer.name = this.signupForm.get('name').value
    newCustomer.phoneNumber = this.signupForm.get('phoneNumber').value
    newCustomer.address = this.signupForm.get('address').value
    newCustomer.email = this.signupForm.get('email').value

    this.customerService.signUp(newCustomer).subscribe(
      resp => {
        const customer: Customer = resp.customer

        if (customer != null) {
          this.sessionService.setCustomerIsLogin(true)
          this.sessionService.setCurrentCustomer(customer)
        }

        this.router.navigate(['/main'])
      },
      error => {
        console.log(error)
        this.displayErrorSnackBar(error)
      }
    )
  }

  displayErrorSnackBar(message: string) {
    this.snackBar.open(message, 'Dismiss')
  }
}
