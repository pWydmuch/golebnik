import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';


@Component({
  selector: 'app-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.css']
})
export class FieldComponent implements OnInit {
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
