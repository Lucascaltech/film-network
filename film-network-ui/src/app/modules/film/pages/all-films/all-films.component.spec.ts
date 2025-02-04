import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllFilmsComponent } from './all-films.component';

describe('AllFilmsComponent', () => {
  let component: AllFilmsComponent;
  let fixture: ComponentFixture<AllFilmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllFilmsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllFilmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
