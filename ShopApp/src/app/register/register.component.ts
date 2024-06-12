import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  //Khai bao cac bien tuong ung voi truong du lieu ma form post len
  phone: string;
  password: string;
  retypePassword: string;
  fullname: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor(){
    this.phone = "";
    this.password= "";
    this.retypePassword = "";
    this.fullname = "";
    this.address = "";
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phone}`);
  }

  register(){
    const message = `phone: ${this.phone}` +
      `password: ${this.password}` + 
      `retypePassword: ${this.retypePassword}` + 
      `address: ${this.address}` + 
      `fullName: ${this.fullname}` + 
      `Date Of Birth: ${this.dateOfBirth}` + 
      `isAccepted: ${this.isAccepted}`;

    alert(message);
  }
}
