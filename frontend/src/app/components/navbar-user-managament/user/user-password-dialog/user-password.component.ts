import {Observable} from "rxjs";
import {UserService} from "src/app/services/user.service";
import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormGroupDirective,
  FormControl,
  NgForm,
} from "@angular/forms";
import {TokenStorageService} from "src/app/services/auth/token-storage.service";
import {stringify} from "querystring";

import {ToastrService} from "ngx-toastr";
import {MatDialogRef, ErrorStateMatcher} from "@angular/material";

class CrossFieldErrorMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    return control.dirty && form.invalid;
  }
}

@Component({
  selector: "app-user-password",
  templateUrl: "./user-password.component.html",
  styleUrls: ["./user-password.component.css"],
})
export class UserPasswordComponent implements OnInit {
  birthdate = new Date(new Date().getTime() - 3888000000);
  myForm: FormGroup;
  hide = true;
  hideNew = true;
  hideConfirm = true;
  errorMatcher = new CrossFieldErrorMatcher();

  constructor(
    private fb: FormBuilder,
    private us: UserService,
    public tokenService: TokenStorageService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<UserPasswordComponent>
  ) {
  }

  ngOnInit() {
    this.myForm = this.fb.group(
      {
        oldPassword: [
          "",
          [
            Validators.required,
            Validators.minLength(6),
            Validators.pattern("[^\\s]+"),
            Validators.maxLength(30),
          ],
        ],
        newPassword: [
          "",
          [
            Validators.required,
            Validators.minLength(6),
            Validators.pattern("[^\\s]+"),
            Validators.maxLength(30),
          ],
        ],
        confirmPassword: [
          "",
          [
            Validators.required,
            Validators.minLength(6),
            Validators.pattern("[^\\s]+"),
            Validators.maxLength(30),
          ],
        ],
      },
      {
        validator: this.passwordValidator,
      }
    );
  }

  passwordValidator(form: FormGroup) {
    const condition =
      form.get("newPassword").value !== form.get("confirmPassword").value;
    return condition ? {passwordDoNotMatch: true} : null;
  }

  submit() {
    // this.userService
    //   .postUser(
    this.us
      .updatePassword(this.oldPasswordInput.value, this.newPasswordInput.value)
      .subscribe(
        (res) => {
          this.toastr.success("Successful change", "", {
            positionClass: "toast-top-center",
          });
          this.dialogRef.close();
        },
        (err) => {
          console.log(err)
          this.toastr.error(err.error.text, "", {
              positionClass: "toast-top-center",
            }
          )
        }
      );
  }

  get oldPasswordInput() {
    return this.myForm.get("oldPassword");
  }

  get newPasswordInput() {
    return this.myForm.get("newPassword");
  }

  get confirmPasswordInput() {
    return this.myForm.get("confirmPassword");
  }
}
