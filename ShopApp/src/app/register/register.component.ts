import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {Router} from '@angular/router'
import { UserService } from '../services/user.service';
import { RegisterDTO } from '../dtos/register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild(`registerForm`) registerForm!: NgForm;

  //Khai bao cac bien tuong ung voi truong du lieu ma form post len
  fullname: string;
  phone: string;
  address: string;
  password: string;
  retypePassword: string;
  isAccepted: boolean;
  dateOfBirth: Date;
  //Inject dependencies http, router
  constructor(
    private router: Router,
    private userService: UserService
  ){
    this.phone = "11223344";
    this.password= "123456";
    this.retypePassword = "123456";
    this.fullname = "Thien Vu Test";
    this.address = "dc 123";
    this.isAccepted = true;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phone}`);
  }

  register(){
    debugger
    const message = `phone: ${this.phone}` +
      `password: ${this.password}` + 
      `retypePassword: ${this.retypePassword}` + 
      `address: ${this.address}` + 
      `fullName: ${this.fullname}` + 
      `Date Of Birth: ${this.dateOfBirth}` + 
      `isAccepted: ${this.isAccepted}`;
    alert(message);
    
    const registerDTO:RegisterDTO = {
      "fullname": this.fullname,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 2 //User role
    }
    
    this.userService.register(registerDTO)
    .subscribe({
      next: (response: any) => {
        debugger
        this.router.navigate(['/login']);
        // if(response && (response.status === 200 || response.status === 201)){
        //   this.router.navigate(['/login']);
        // }else{
        //   //Xu ly neu khong thanh cong
        // }
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        debugger
        alert(`Cannot register, error: ` + error.error);
        console.error("Register not success!", error);
      }
    });
  }

  checkPasswordMatch(){
    if(this.password !== this.retypePassword){
      this.registerForm.form.controls[`retypePassword`].setErrors({'Password Miss Match': true});
    } else {
      this.registerForm.form.controls[`retypePassword`].setErrors(null);

    }
  }
  checkAge(){
    if(this.dateOfBirth){
      const today = new Date();
      const birthDay = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDay.getFullYear();
      const monthDiff = today.getMonth() - birthDay.getMonth();

      if(monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDay.getDate())){
        age--;
      }
  
      if(age < 18){
        this.registerForm.form.controls[`dateOfBirth`].setErrors({'Invalid Age': true});
      }else{
        this.registerForm.form.controls[`dateOfBirth`].setErrors(null);
      }
    }
  }


}
