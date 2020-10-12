import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';


@Component({
  selector: 'app-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.css']
})
export class FieldComponent implements OnInit {
  @Input() value: string;
  @Input() winColor: boolean;
  @Output() click = new EventEmitter<string>();
  constructor() { }

  ngOnInit() {
  }

  handleClick() {
    this.click.emit();
  }

}
