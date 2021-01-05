import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicTacToeCardComponent } from './tic-tac-toe-card.component';

describe('TicTacToeCardComponent', () => {
  let component: TicTacToeCardComponent;
  let fixture: ComponentFixture<TicTacToeCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicTacToeCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicTacToeCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
