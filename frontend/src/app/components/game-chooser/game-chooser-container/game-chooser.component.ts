import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-game-chooser',
  templateUrl: './game-chooser.component.html',
  styleUrls: ['./game-chooser.component.css']
})
export class GameChooserComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }
  goToLobby(gameName: string){
    this.router.navigateByUrl('/lobby', {state: {gameName, playersNumber: 2}});
  }

}
