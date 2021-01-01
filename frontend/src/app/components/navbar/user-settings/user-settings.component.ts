import { Observable } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormGroupDirective, FormControl } from '@angular/forms';
import { TokenStorageService } from 'src/app/services/auth/token-storage.service';
import { stringify } from 'querystring';
import { User } from 'src/app/models/user';
import { ToastrService } from 'ngx-toastr';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

 


  birthdate = new Date((new Date().getTime() - 3888000000));
  myForm: FormGroup;

  constructor(private fb: FormBuilder, private us: UserService, public tokenService:TokenStorageService,
    private toastr: ToastrService,public dialogRef: MatDialogRef<UserSettingsComponent>) {this.setfirstnameInput() }

  ngOnInit() {
    this.myForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
      firstname: ["", [Validators.required, Validators.minLength(2)]],
      lastname: ["", [Validators.required, Validators.minLength(2)]],
      gender: "",
      birthday: ""
    })

  }
  setfirstnameInput(){
    this.us.getUserData().subscribe(u =>{
      this.myForm = this.fb.group({
        email: [u.email, [Validators.required, Validators.email]],
        firstname: [u.firstName, [Validators.required, Validators.minLength(2)]],
        lastname: [u.lastName, [Validators.required, Validators.minLength(2)]],
        gender: u.gender,
        birthday: new FormControl(new Date(u.birthday))
      })
   
    // this.birthdayDate = u.birthday
 
   // console.log(this.u.birthday)
    console.log(u.gender)
    });
}

  get emailInput() {
    return this.myForm.get("email");
  }

  get firstnameInput() {
    return this.myForm.get("firstname");
  }
  get lastnameInput() {
    return this.myForm.get("lastname");
  }
  get genderInput() {
    return this.myForm.get("gender");
  }
  get birthdayInput() {
    return this.myForm.get("birthday");
  }

  submit() {
    // this.userService
    //   .postUser(
      this.us.updateUser(
        new User(
          -1,
          null,
          null,
          null,
          this.tokenService.getUser(),
          null,
          this.firstnameInput.value,
          this.lastnameInput.value,
          this.emailInput.value,
          new Date(this.birthdayInput.value),
          this.genderInput.value
        )
      )
      .subscribe(
        res => {
          this.toastr.success("Zmieniono poprawnie","", { positionClass:'toast-top-center'})
          this.dialogRef.close();
        
      
        },
        err => this.toastr.error(err.error,"", { positionClass:'toast-top-center'})
      );
  }

}
