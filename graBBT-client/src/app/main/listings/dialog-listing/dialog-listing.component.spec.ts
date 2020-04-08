import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogListingComponent } from './dialog-listing.component';

describe('DialogListingComponent', () => {
  let component: DialogListingComponent;
  let fixture: ComponentFixture<DialogListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
