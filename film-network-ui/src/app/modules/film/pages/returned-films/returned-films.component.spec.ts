import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnedFilmsComponent } from './returned-films.component';

describe('ReturnedFilmsComponent', () => {
  let component: ReturnedFilmsComponent;
  let fixture: ComponentFixture<ReturnedFilmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReturnedFilmsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReturnedFilmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
