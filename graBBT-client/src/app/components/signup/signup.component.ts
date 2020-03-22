import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'

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
      Validators.compose([Validators.required, Validators.maxLength(32)]),
    ],
    password: [null, Validators.required],
    email: [null, Validators.compose([Validators.required, Validators.email])],
    phone: [
      null,
      Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]*$'),
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
    ],
    address: [null, Validators.required],
  })

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {}

  onSubmit() {
    console.log('submitted')
  }
}
