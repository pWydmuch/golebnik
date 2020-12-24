import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[appGame]'
})
export class GameDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
