import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-tic-tac-toe-field',
  templateUrl: './tic-tac-toe-field.component.html',
  styleUrls: ['./tic-tac-toe-field.component.css']
})
export class TicTacToeFieldComponent implements OnInit {

  @Input() value: string;
  @Input() winColor: boolean;
  @Output() clickChild = new EventEmitter<string>();
  constructor() { }

  ngOnInit() {
  }

  handleClick() {
    console.log("child clicked")
    this.clickChild.emit();
  }
}
