import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnFilmModalComponent } from './return-film-modal.component';

describe('ReturnFilmModalComponent', () => {
  let component: ReturnFilmModalComponent;
  let fixture: ComponentFixture<ReturnFilmModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReturnFilmModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReturnFilmModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
