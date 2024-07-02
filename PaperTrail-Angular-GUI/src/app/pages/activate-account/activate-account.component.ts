import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {

  message: string = '';
  isOkay: boolean = true;
  isSubmitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  onCodeCompleted(otp: string) {
    this.confirmAccount(otp);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(otp: string) {
    this.authService.activateAccount({
      'token':otp
      }).subscribe({
        next: () =>{
          this.message = 'Account activated successfully, you can now login.';
          this.isOkay = true;
          this.isSubmitted = true;
        },
        error: err => {
          this.message = err.error.error;
          this.isOkay = false;
          this.isSubmitted = true;
        }

    })
  }
}
