import { Component, OnInit, Inject } from '@angular/core'
import { FormBuilder, FormArray, Validators } from '@angular/forms'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { Listing } from 'src/app/services/listing/listing'

@Component({
  selector: 'app-dialog-listing',
  templateUrl: './dialog-listing.component.html',
  styleUrls: ['./dialog-listing.component.css'],
})
export class DialogListingComponent implements OnInit {
  orderForm = this.fb.group({
    sizeChoice: ['', Validators.required],
    sugarChoice: ['', Validators.required],
    iceChoice: ['', Validators.required],
    toppingFormArray: this.fb.array([]),
    quantity: [1, Validators.min(1)],
  })

  orderPrice: number

  get toppingFormArray() {
    return this.orderForm.get('toppingFormArray') as FormArray
  }

  get quantity() {
    return this.orderForm.get('quantity').value
  }

  toppingArr = []

  constructor(
    public dialogRef: MatDialogRef<DialogListingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Listing,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.toppingArr = Object.entries(this.data.toppingOptions)
    // console.log(this.toppingArr)
    this.toppingArr.forEach(e => {
      this.toppingFormArray.push(this.fb.control(false))
    })
    this.orderPrice = this.data.basePrice

    this.onFormChanges()
  }

  // calculate new orderPrice on form change
  onFormChanges(): void {
    this.orderForm.valueChanges.subscribe(form => {
      let newPrice = this.data.basePrice
      console.log('form changed!')
      if (form.sizeChoice) {
        console.log(
          form.sizeChoice + ' ' + this.data.sizeOptions[form.sizeChoice]
        )
        newPrice += this.data.sizeOptions[form.sizeChoice]
      }
      if (form.sugarChoice) {
        console.log(
          form.sugarChoice + ' ' + this.data.sugarOptions[form.sugarChoice]
        )
        newPrice += this.data.sugarOptions[form.sugarChoice]
      }
      if (form.iceChoice) {
        console.log(form.iceChoice + ' ' + this.data.iceOptions[form.iceChoice])
        newPrice += this.data.iceOptions[form.iceChoice]
      }
      console.log(form.toppingFormArray)
      form.toppingFormArray.forEach((selected, index) => {
        if (selected) {
          newPrice += this.toppingArr[index][1]
        }
      })
      console.log(form.quantity)
      newPrice *= form.quantity
      this.orderPrice = newPrice
    })
  }

  onCancel(): void {
    this.dialogRef.close()
  }

  decrementQty() {
    if (this.quantity > 1) {
      this.orderForm.get('quantity').setValue(this.quantity - 1)
    }
  }

  incrementQty() {
    this.orderForm.get('quantity').setValue(this.quantity + 1)
  }
}
