import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Connect4CardComponent } from './connect4-card.component';

describe('Connect4CardComponent', () => {
  let component: Connect4CardComponent;
  let fixture: ComponentFixture<Connect4CardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Connect4CardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Connect4CardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
