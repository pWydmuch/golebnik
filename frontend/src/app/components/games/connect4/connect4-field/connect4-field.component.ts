import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-connect4-field',
  templateUrl: './connect4-field.component.html',
  styleUrls: ['./connect4-field.component.css']
})
export class Connect4FieldComponent implements OnInit {

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
