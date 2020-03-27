import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountSheetComponent } from './account-sheet.component';

describe('AccountSheetComponent', () => {
  let component: AccountSheetComponent;
  let fixture: ComponentFixture<AccountSheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountSheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
