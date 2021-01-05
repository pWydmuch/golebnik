import { Injectable } from '@angular/core';

const TOKEN_KEY = 'token';
const USER_KEY = 'login';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  isLoggedIn:boolean;

  constructor() { }

  signOut() {
    sessionStorage.clear();
    this.isLoggedIn=false;
  }

  public saveToken(token: string) {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.setItem(TOKEN_KEY, token);
    this.isLoggedIn=true;
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user) {
    sessionStorage.removeItem(USER_KEY);
    sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }
}
