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
    quantity: [0, Validators.min(1)],
  })

  get toppingFormArray() {
    return this.orderForm.get('toppingFormArray') as FormArray
  }

  toppingArr = []

  constructor(
    public dialogRef: MatDialogRef<DialogListingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Listing,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.toppingArr = Object.entries(this.data.toppingOptions)
    this.toppingArr.forEach(e => {
      this.toppingFormArray.push(this.fb.control(false))
    })
  }

  onCancel(): void {
    this.dialogRef.close()
  }

  decrementQty() {
    if (this.orderForm.get('quantity').value > 0) {
      this.orderForm
        .get('quantity')
        .setValue(this.orderForm.get('quantity').value - 1)
    }
  }

  incrementQty() {
    this.orderForm
      .get('quantity')
      .setValue(this.orderForm.get('quantity').value + 1)
  }
}
