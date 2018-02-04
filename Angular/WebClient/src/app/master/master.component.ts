import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { trigger,state,style,transition,animate,keyframes } from '@angular/animations';
import { Decision } from "../decision2";
import { Player } from "../player";
import {MatSnackBar} from '@angular/material';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { ThankyouDialogComponent } from 'app/thankyou-dialog/thankyou-dialog.component';




@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css'],
  animations: [

    trigger('hideLogin', [
      state('loginBefore', style({
        top: '0px',
        opacity: 1
      })),
      state('loginAfter', style({
        top: '-600px',
        opacity: 0
      })),
      transition('loginBefore <=> loginAfter', animate('200ms ease-in')),
    ]),

    trigger('animateRegister', [
      state('registerBefore', style({
      })),
      state('registerAfter', style({
      })),
      transition('registerBefore <=> registerAfter', animate('200ms ease-in')),
    ]),
    
    trigger('showUpload', [
      state('uploadBefore', style({
        opacity: 0,
        top: '0px',
      })),
      state('uploadAfter', style({  
        opacity: 1,
        top: '-370px',
      })),
      transition('uploadBefore <=> uploadAfter', animate('200ms ease-in')),
    ]),

    trigger('hideUpload', [
      state('hideUploadBefore', style({
        opacity:1
      })),
      state('hideUploadAfter', style({
        opacity:0
      })),
      transition('hideUploadBefore <=> hideUploadAfter', animate('500ms ease-in')),
    ]),
    

    trigger('logoTransition', [
      state('logoBefore', style({
        transform: 'scale(1)'
      })),
      state('logoAfter', style({  
        transform: 'scale(0.4)'
      })),
      transition('logoBefore <=> logoAfter', animate('200ms ease-in')),
    ]),
    
    trigger('showTimestamps', [
      state('timeBefore', style({
        top: '0px',
        opacity: 0
      })),
      state('timeAfter', style({  
        top: '-710px',
        opacity: 1
      })),
      transition('timeBefore <=> timeAfter', animate('200ms ease-in')),
    ])
  ]
})

export class MasterComponent implements OnInit {
  private rest;

  /** Progress Bar **/
  color = 'primary';
  mode = 'determinate';
  value = 0;
  bufferValue = 75;
  uploaded:boolean;

  //Animation Var
  logoStyle: string = 'logoBefore';
  loginStyle: string = 'loginBefore';
  registerStyle: string = 'registerBefore';
  uploadState: string = 'uploadBefore';
  projectState: string = 'projectBefore';
  hideUpload: string = 'hideUploadBefore';
  hideProject: string = 'hideProjectBefore';
  timeBox: string = 'timeBefore';

  showTimeBox: boolean = false;
  allowImageMargin: boolean = false;

  loading: boolean = false;

  //ExpansionPanel Var
  public decisions:Decision[] = [];


  //Video Source
  src = "http://vm18.htl-leonding.ac.at:8080/videos/video1.mp4";


  subDecisions = [
    {value: 'leftOrRight', viewValue: 'Left or Right'},
    {value: 'upOrDown', viewValue: 'Up or Down'},
    {value: 'aOrB', viewValue: 'A or B'}
  ];

  public n: number = 1;

  constructor(rest:RestService, public snackBar: MatSnackBar, public dialog: MatDialog) {
    this.rest = rest;
    this.value = 0;
  }

  ngOnInit() {
    
    this.decisions.push({title: "Entscheidung 1", methodname: "", timestampMin: "", timestampSek: "", timestampToAMin: "", timestampToASek: "", timestampToBMin: "", timestampToBSek: ""});
    this.uploaded = true;

    /* erstes Request an Server 
    this.students.push(new Student(null, "", "", null, null, null));
    this.rest.findTeams().subscribe(data => {this.teams = data});
    */
  }

  timer: any;

  export(){
    this.loading = true;
    console.log("export started!")
    this.rest.uploadInteractions(this.decisions).subscribe(data => {console.log(data);
      this.timer = setTimeout(() => {
        this.loading = false
        this.thankYou()
    }, 500);
    });
  }
    
  //Animation Functions
  uploadNext() {
    this.showTimeBox = true;
    this.allowImageMargin = true;
    this.getImgMarginTop();
    this.logoStyle = (this.logoStyle === 'logoBefore' ? 'logoAfter' : 'logoBefore');
    this.hideUpload = (this.hideUpload === 'hideUploadBefore' ? 'hideUploadAfter' : 'hideUploadBefore');
    this.timeBox = (this.timeBox === 'timeBefore' ? 'timeAfter' : 'timeBefore');    
    this.allowLogout = false;
  }

  //LOGIN - REGISTER
  allowRegister:boolean = false;
  allowLogout:boolean = false;
  email:string;
  password:string;

  players :Array<Player> = [];

  register(){
    this.allowRegister = true;
    this.allowLogout = false;
    this.registerAnimation()
  }

  login() {
    this.rest.checkPlayer().subscribe(data => {this.players = data
      this.players.forEach(p => {
        if (p.email == this.email && p.password == this.password) {
          this.allowRegister = true;
          this.allowLogout = true;
          this.hideLogin();
        }
      });
      if (!this.allowLogout) {
        this.snackBar.open("E-Mail oder Passwort nicht korrekt!", "OK", {
          duration: 2000,
        });
      }
    });
  }

  logout(){
    window.location.reload();
  }

  loginRegister() {
    console.log(this.email)
    console.log(this.password)
    this.rest.createPlayer(this.email, this.password).subscribe(data => {
      if(data){
        this.hideLogin();
        this.allowLogout = true;
      } else {
        this.snackBar.open("Diese E-Mail adresse ist bereits vergeben!", "OK", {
          duration: 2000,
        });
      }});
  }

  backLogin(){
    window.location.reload();
  }

  hideLogin() {
    this.loginStyle = (this.loginStyle === 'loginBefore' ? 'loginAfter' : 'loginBefore');
    this.uploadState = (this.uploadState === 'uploadBefore' ? 'uploadAfter' : 'uploadBefore');
  }

  registerAnimation(){
    this.registerStyle = (this.registerStyle === 'registerBefore' ? 'registerAfter' : 'registerBefore');
  }

  getImgMarginTop() {
    if(!this.allowImageMargin) {
      return 70;
    } else {
      return 10;
    }
    
  }

  //FileUpload
  uploadFile(event) {
    //Timeout - > Testzweck
    setTimeout(() => {
      this.n = this.n + 10;
      this.value = 100;  
      this.uploaded = false;
    }, 1000);
        
    let files = event.target.files;
    if (files.length > 0) {
      this.rest.uploadFile(event.target.files[0]);     
    }
  }

  endFalse(nextStep:string) {
    //Entscheidung 1 wird disabled
    //löschen Button einführen

    //Export Button darf nur möglich sein, wenn alle Entscheidungen fertig sind (wenn alle "Endet hier der Film" mit "Ja" beantwortet werden konnten)

    var count = this.decisions.length+1;
    this.decisions.push({title: "Entscheidung "+ count + " - " + nextStep, methodname: "", timestampMin: "", timestampSek: "", timestampToAMin: "", timestampToASek: "", timestampToBMin: "", timestampToBSek: ""});
  }


  
  thankYou(): void {
    let dialogRef = this.dialog.open(ThankyouDialogComponent, {
      width: '500px',
      data: { name: "asdf", animal: "jklö" }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
    });
  }


  //Gedanken machen bei welchen Bedingungen nur das eine Ende abgefragt wird, nicht alle.... 
  //Mit Berni ausmachen wie das Json aussehen soll, wenn der Film endet mit "Ja" gedrückt würde

  // Decision push --> methodname = death

  endTrue(title:string) {
    this.expand(title);
  }

  expand(title:string){
   /* if (title == this.newTitle) {
      return true;
    } else {
      return false;
    }*/
  }
}
