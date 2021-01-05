import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormGroupDirective,
} from "@angular/forms";
import { UserService } from "src/app/services/user.service";
import { ToastrService } from "ngx-toastr";
import { AuthenticationService } from 'src/app/services/auth/authentication.service';
import { TokenStorageService } from 'src/app/services/auth/token-storage.service';
import {Router} from "@angular/router";

@Component({
  selector: "app-login-form",
  templateUrl: "./login-form.component.html",
  styleUrls: ["./login-form.component.css"],
})
export class LoginFormComponent implements OnInit {
  @Output() onHideLogin = new EventEmitter<boolean>();

  setHideLogin() {
    this.onHideLogin.emit(true);
  }

  myForm: FormGroup;

  hide = true;
  isLoggedIn = false;

  constructor(
    private authService : AuthenticationService,
    private tokenService : TokenStorageService, 
    private fb: FormBuilder,
    private userService: UserService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit() {
    this.myForm = this.fb.group({
      login: ["", [Validators.required, Validators.minLength(5)]],
      password: ["", [Validators.required, Validators.minLength(6)]],
    });
  }

  get loginInput() {
    return this.myForm.get("login");
  }

  get passwordInput() {
    return this.myForm.get("password");
  }

  submit() {
    this.authService
      .login(
       {
          login:  this.loginInput.value,
          password: this.passwordInput.value,
       }
      )
      .subscribe(
        (res) => {
          this.toastr.success("Successful login", "", {
            positionClass: "toast-top-center",
          });
        
          this.setHideLogin();
          this.tokenService.saveToken(res.token);
          this.tokenService.saveUser(res.login);
          this.router.navigate(['/game-chooser']);
          // this.isLoginFailed = false;
     
          // this.roles = this.tokenStorage.getUser().roles;
          // location.reload();
        },
        (err) =>
          this.toastr.error(err.error.text, "", {
            positionClass: "toast-top-center",
          })
      );
  }
}
