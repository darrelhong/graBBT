import { Component, OnInit } from '@angular/core'

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css'],
})
export class ErrorComponent implements OnInit {
  errorMessage: string

  constructor() {}

  ngOnInit() {
    this.errorMessage = window.history.state.error
  }
}
