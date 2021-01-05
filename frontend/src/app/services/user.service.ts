import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

import { tap, map } from "rxjs/operators";
import {User} from "../components/navbar-user-managament/user/user";


@Injectable({
  providedIn: "root",
})
export class UserService {
  private url = environment.basicUrl + "/users";

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.url);
    // .pipe(tap((_) => console.log("fetched users")));
  }

  getUsr(login): Observable<User>{
    const params = new HttpParams().set("login", login);
    return this.http.get<User>(`${environment.basicUrl}/user`,{params});
  }

  getUser(loginVal): Observable<User> {
    let user = this.getUsers().pipe(
      map(u=>u.find(usr => usr.login))
    );
    return user;
    
    // .pipe(tap((_) => console.log("fetched users")));
  }

  getUserData(): Observable<User> {
    return this.http.get<User>(`${environment.basicUrl}/my-data`);
  }

  postUser(user: User): Observable<User> {
    return this.http.post<User>(this.url, user);
  }
  updateUser(user: User): Observable<User>{
    return this.http.put<User>(`${environment.basicUrl}/my-data`,user);
  }

  updatePassword(oldPassword: string, newPassword: string): Observable<User> {
    console.log("service")
    return this.http.put<User>(`${environment.basicUrl}/my-password`,{oldPassword, newPassword});
  }

}
