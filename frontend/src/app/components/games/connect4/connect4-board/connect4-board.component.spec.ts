import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Connect4BoardComponent } from './connect4-board.component';

describe('Connect4BoardComponent', () => {
  let component: Connect4BoardComponent;
  let fixture: ComponentFixture<Connect4BoardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Connect4BoardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Connect4BoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
