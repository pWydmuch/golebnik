import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-join-form',
  templateUrl: './user-join-form.component.html',
  styleUrls: ['./user-join-form.component.css']
})
export class UserJoinFormComponent implements OnInit {

  private username: string;


  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  send() {
    console.log(this.username)
    this.router.navigateByUrl('/chat', {state: {data: this.username}});
  }

}
