import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MessageType} from "../../../chat/model/chat-message";
import {Connect4FieldContentType} from "../model/connect4-field-content-dto";

@Component({
  selector: 'app-connect4-field',
  templateUrl: './connect4-field.component.html',
  styleUrls: ['./connect4-field.component.css']
})
export class Connect4FieldComponent implements OnInit {

  @Input() value: string;
  @Input() winColor: boolean;
  @Output() clickChild = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit() {
  }

  handleClick() {
    console.log("child clicked")
    this.clickChild.emit();
  }

  getPlayerClass(value) {
    return {
      'red': this.value === Connect4FieldContentType.RED,
      'black': this.value === Connect4FieldContentType.BLACK
    };
  }
}
