import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { Customer } from 'src/app/services/customer/customer';
import { FormBuilder, Validators, NgForm } from '@angular/forms'
import { CustomerService } from 'src/app/services/customer/customer.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  currentCustomer: Customer
  updateCustomer: Customer
  editView: boolean

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  updateAccountForm = this.formBuilder.group({
    name:[
      null,
      Validators.compose([Validators.required, Validators.maxLength(64)])
    ],
    email: [null, Validators.required],
    phoneNumber: [
      null,
      Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]*$'),
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
    ],
    address: [null, Validators.required]
  })

  constructor(
    private sessionService: SessionService,
    private customerService: CustomerService,
    private formBuilder: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar
  ) 
  { 
    this.editView = false;
    this.resultSuccess = false;
    this.resultError = false;
    this.updateCustomer = new Customer()
  }

  ngOnInit() {
    this.currentCustomer = this.sessionService.getCurrentCustomer();
    console.log(this.currentCustomer.password)
    this.updateCustomer.customerId = this.currentCustomer.customerId;
    this.updateCustomer.username = this.currentCustomer.username
    this.updateCustomer.password = this.currentCustomer.password
    this.updateCustomer.bbPoints = this.currentCustomer.bbPoints
    this.updateCustomer.name = this.currentCustomer.name
    this.updateCustomer.phoneNumber = this.currentCustomer.phoneNumber
    this.updateCustomer.email = this.currentCustomer.email
    this.updateCustomer.address = this.currentCustomer.address
 
  }

  updateAccount(updateAccountForm: NgForm)
  {
    console.log("update account form")

    if (updateAccountForm.valid)
    {
      this.customerService.updateCustomer(this.updateCustomer).subscribe(
        resp => {
          this.resultSuccess = true;
          this.resultError = false;
  
          this.sessionService.setCurrentCustomer(this.updateCustomer) //kiv
  
          this.displayErrorSnackBar('Customer details updated successfully')
          this.editView = false;
          this.router.navigate(['/main/account']) 
          .then(() => {
            window.location.reload();
          });
          
        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.displayErrorSnackBar('An error occured while updating customer details')
        
          console.log('********** account.component.ts: ' + error);
        }
      )
    }
    else 
    {
      this.displayErrorSnackBar('One or more fields is invalid. Please try again!');
    }
  }

  displayErrorSnackBar(message: string) {
    this.snackBar.open(message, 'Dismiss', { duration: 5000 })
  }

  clear()
  {
    this.updateCustomer.customerId = this.currentCustomer.customerId;
    this.updateCustomer.username = this.currentCustomer.username
    this.updateCustomer.password = this.currentCustomer.password
    this.updateCustomer.bbPoints = this.currentCustomer.bbPoints
    this.updateCustomer.name = this.currentCustomer.name
    this.updateCustomer.phoneNumber = this.currentCustomer.phoneNumber
    this.updateCustomer.email = this.currentCustomer.email
    this.updateCustomer.address = this.currentCustomer.address
  }

  cancel()
  {
    //reset back
    this.clear();
    this.editView = false;
  }
}
