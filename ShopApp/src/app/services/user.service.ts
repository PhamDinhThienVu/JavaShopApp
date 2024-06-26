import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/register.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiURL = "http://localhost:8088/api/v1/users/register";
  constructor(private http: HttpClient,) { }
  register(registerDTO: RegisterDTO):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
   });
   return this.http.post(this.apiURL, registerDTO, {headers: headers})
  }
}
