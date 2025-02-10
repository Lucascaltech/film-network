import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmInfoModalComponent } from './film-info-modal.component';

describe('FilmInfoModalComponent', () => {
  let component: FilmInfoModalComponent;
  let fixture: ComponentFixture<FilmInfoModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmInfoModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilmInfoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
