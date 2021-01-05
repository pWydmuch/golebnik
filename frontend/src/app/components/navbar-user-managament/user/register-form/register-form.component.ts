import {
  Component,
  OnInit,
  ViewChild,
  Output,
  EventEmitter,
} from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormGroupDirective,
} from "@angular/forms";
import { UserService } from "src/app/services/user.service";

import { ToastrService } from "ngx-toastr";
import { AuthenticationService } from "src/app/services/auth/authentication.service";
import {User} from "../user";

@Component({
  selector: "app-register-form",
  templateUrl: "./register-form.component.html",
  styleUrls: ["./register-form.component.css"],
})
export class RegisterFormComponent implements OnInit {
  @Output() onHide = new EventEmitter<boolean>();

  maxDate = new Date("1/1/2015");
  setHide() {
    this.onHide.emit(true);
  }

  myForm: FormGroup;
  hide = true;

  constructor(
    private authService: AuthenticationService,
    private fb: FormBuilder,
    private userService: UserService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.myForm = this.fb.group({
      email: [
        "",
        [Validators.required, Validators.email, Validators.maxLength(50)],
      ],
      login: [
        "",
        [
          Validators.required,
          Validators.minLength(5),
          Validators.pattern("^[a-zA-Z0-9]*$"),
          Validators.maxLength(30),
        ],
      ],
      password: [
        "",
        [
          Validators.required,
          Validators.minLength(6),
          Validators.pattern("[^\\s]+"),
          Validators.maxLength(30),
        ],
      ],
      firstname: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(30),
        ],
      ],
      lastname: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(30),
        ],
      ],
      gender: "M",
      birthday: "",
    });
  }

  @ViewChild(FormGroupDirective, { static: false })
  formDirective: FormGroupDirective;

  submit() {
    // this.userService
    //   .postUser(
    this.authService
      .register(
        new User(
          -1,
          this.loginInput.value,
          this.passwordInput.value,
          this.firstnameInput.value,
          this.lastnameInput.value,
          this.emailInput.value,
          new Date(this.birthdayInput.value),
          this.genderInput.value
        )
      )
      .subscribe(
        (res) => {
          this.toastr.success("Successful registration", "", {
            positionClass: "toast-top-center",
          });
          this.setHide();
        },
        (err) =>
          this.toastr.error(err.error.text, "", {
            positionClass: "toast-top-center",
          })
      );
  }

  get emailInput() {
    return this.myForm.get("email");
  }
  get passwordInput() {
    return this.myForm.get("password");
  }
  get loginInput() {
    return this.myForm.get("login");
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
}
