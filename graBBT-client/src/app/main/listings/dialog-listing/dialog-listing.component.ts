import { Component, OnInit, Inject } from '@angular/core'
import { FormBuilder, FormArray, Validators } from '@angular/forms'
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialog,
} from '@angular/material/dialog'
import { Listing } from 'src/app/services/listing/listing'
import { CartService } from 'src/app/services/cart/cart.service'
import { Cart } from 'src/app/services/cart/cart'
import { CartItem } from 'src/app/services/cart/cart-item'
import { Outlet } from 'src/app/services/listing/outlet'
import { SidenavService } from 'src/app/services/sidenav.service'
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component'

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

  subtotal: number
  cart: Cart

  get toppingFormArray() {
    return this.orderForm.get('toppingFormArray') as FormArray
  }

  get quantity() {
    return this.orderForm.get('quantity').value
  }

  toppingArr = []

  constructor(
    public dialogRef: MatDialogRef<DialogListingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { listing: Listing; outlet: Outlet },
    private fb: FormBuilder,
    private cartService: CartService,
    private sidenavService: SidenavService,
    private cfmDialog: MatDialog
  ) {}

  ngOnInit() {
    this.toppingArr = Object.entries(this.data.listing.toppingOptions)
    // console.log(this.toppingArr)
    this.toppingArr.forEach(e => {
      this.toppingFormArray.push(this.fb.control(false))
    })
    this.subtotal = this.data.listing.basePrice
    this.onFormChanges()

    this.cartService.cart.subscribe(cart => (this.cart = cart))
  }

  addToCart() {
    const newSelectedOptions: string[] = []
    newSelectedOptions.push(this.orderForm.get('sizeChoice').value)
    newSelectedOptions.push(this.orderForm.get('sugarChoice').value)
    newSelectedOptions.push(this.orderForm.get('iceChoice').value)
    this.toppingFormArray.controls.forEach((control, i) => {
      if (control.value) {
        newSelectedOptions.push(this.toppingArr[i][0])
      }
    })
    const item: CartItem = {
      listing: this.data.listing,
      qty: this.quantity,
      selectedOptions: newSelectedOptions,
      subtotal: this.subtotal,
    }
    console.log(item)

    if (this.cart.outlet) {
      if (this.cart.outlet.outletId === this.data.outlet.outletId) {
        // check for exactly the same order
        let hasSame = false

        for (var c of this.cart.cartItems) {
          // console.log("Comparing listing id")
          // console.log(item.listing.listingId)
          // console.log(c.listing.listingId)

          if (item.listing.listingId == c.listing.listingId) {
            // console.log("Comparing selected options length")
            // console.log(item.selectedOptions.length)
            // console.log(c.selectedOptions.length)

            if (item.selectedOptions.length == c.selectedOptions.length) {
              // console.log("Comparing selected options")
              // console.log(JSON.stringify(item.selectedOptions))
              // console.log(JSON.stringify(c.selectedOptions))

              if (
                JSON.stringify(item.selectedOptions) ==
                JSON.stringify(c.selectedOptions)
              ) {
                // update cart item instead
                // console.log("They are the same!")
                hasSame = true
                c.qty += item.qty
                c.subtotal += item.subtotal

                this.cartService.updateCart(this.cart)
                this.dialogRef.close()
                this.sidenavService.open()
                break
              } else {
                hasSame = false
              }
            } else {
              hasSame = false
            }
          } else {
            hasSame = false
          }
        }

        // adding to cart
        if (!hasSame) {
          this.cart.cartItems.push(item)
          this.cartService.updateCart(this.cart)
          this.dialogRef.close()
          this.sidenavService.open()
        }
      } else {
        const dialogRef = this.cfmDialog.open(ConfirmDialogComponent, {
          width: '400px',
          data: {
            currentOutlet: this.cart.outlet,
            newOutlet: this.data.outlet,
          },
        })

        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.cartService.newCart(item, this.data.outlet)
            this.dialogRef.close()
            this.sidenavService.open()
          } else {
            this.dialogRef.close()
          }
        })
      }
    } else {
      this.cartService.newCart(item, this.data.outlet)
      this.dialogRef.close()
      this.sidenavService.open()
    }
    // if (
    //   this.cart.outlet &&
    //   this.cart.outlet.outletId !== this.data.outlet.outletId
    // ) {
    //   const dialogRef = this.cfmDialog.open(ConfirmDialogComponent, {
    //     width: '400px',
    //     data: { currentOutlet: this.cart.outlet, newOutlet: this.data.outlet },
    //   })

    //   dialogRef.afterClosed().subscribe(result => {
    //     if (result) {
    //       this.cartService.newCart(item, this.data.outlet)
    //       this.dialogRef.close()
    //       this.sidenavService.open()
    //     } else {
    //       this.dialogRef.close()
    //     }
    //   })

    // old
    // const message =
    //   'Start a new order? Press OK to clear current order with ' +
    //   this.cart.outlet.outletName +
    //   ' and start new order with ' +
    //   this.data.outlet.outletName +
    //   '.'
    // const result = confirm(message)
    // if (result) {
    //   this.cartService.newCart(item, this.data.outlet)
    // } else {
    // }
    // } else {
    //   this.cart.cartItems.push(item)
    //   this.cartService.updateCart(this.cart)
    //   this.dialogRef.close()
    //   this.sidenavService.open()
    // }
  }

  // calculate new orderPrice on form change
  onFormChanges(): void {
    this.orderForm.valueChanges.subscribe(form => {
      let newPrice = +this.data.listing.basePrice
      console.log(newPrice)
      console.log('form changed!')
      if (form.sizeChoice) {
        console.log(
          form.sizeChoice + ' ' + this.data.listing.sizeOptions[form.sizeChoice]
        )
        newPrice += this.data.listing.sizeOptions[form.sizeChoice]
        console.log(newPrice)
      }
      if (form.sugarChoice) {
        console.log(
          form.sugarChoice +
            ' ' +
            this.data.listing.sugarOptions[form.sugarChoice]
        )
        newPrice += this.data.listing.sugarOptions[form.sugarChoice]
        console.log(newPrice)
      }
      if (form.iceChoice) {
        console.log(
          form.iceChoice + ' ' + this.data.listing.iceOptions[form.iceChoice]
        )
        newPrice += this.data.listing.iceOptions[form.iceChoice]
        console.log(newPrice)
      }
      console.log(form.toppingFormArray)
      console.log(newPrice)
      form.toppingFormArray.forEach((selected, index) => {
        if (selected) {
          newPrice += this.toppingArr[index][1]
          console.log(newPrice)
        }
      })
      console.log(newPrice)
      console.log(form.quantity)
      newPrice = form.quantity * newPrice
      // to fix decimal place errors
      newPrice = +newPrice.toFixed(2)
      console.log(newPrice)
      this.subtotal = newPrice
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
