import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Connect4FieldComponent } from './connect4-field.component';

describe('Connect4FieldComponent', () => {
  let component: Connect4FieldComponent;
  let fixture: ComponentFixture<Connect4FieldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Connect4FieldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Connect4FieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
