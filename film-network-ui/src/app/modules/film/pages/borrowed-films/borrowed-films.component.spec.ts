import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedFilmsComponent } from './borrowed-films.component';

describe('BorrowedFilmsComponent', () => {
  let component: BorrowedFilmsComponent;
  let fixture: ComponentFixture<BorrowedFilmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BorrowedFilmsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowedFilmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
