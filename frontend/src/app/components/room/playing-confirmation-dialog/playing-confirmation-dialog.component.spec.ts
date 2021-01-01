import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayingConfirmationDialogComponent } from './playing-confirmation-dialog.component';

describe('PlayingConfirmationDialogComponent', () => {
  let component: PlayingConfirmationDialogComponent;
  let fixture: ComponentFixture<PlayingConfirmationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayingConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayingConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
