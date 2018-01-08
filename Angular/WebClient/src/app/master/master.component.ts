import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { trigger,state,style,transition,animate,keyframes } from '@angular/animations';
import { Decision } from "../decision";

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
        visibility: 'hidden'
      })),
      state('timeAfter', style({  
        top: '-720px',
        visibility: 'visible'
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
  uploadState: string = 'uploadBefore';
  hideUpload: string = 'hideUploadBefore';
  timeBox: string = 'timeBefore';
  showTimeBox: boolean = false;
  allowImageMargin: boolean = false;

  //ExpansionPanel Var
  public decisions:Decision[] = [];


  subDecisions = [
    {value: 'leftOrRight', viewValue: 'Left or Right'},
    {value: 'upOrDown', viewValue: 'Up or Down'},
    {value: 'aOrB', viewValue: 'A or B'}
  ];

  public n: number = 1;

  constructor(rest:RestService) {
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

  export(){
    console.log("export started!")
    this.rest.uploadInteractions(this.decisions).subscribe(data => {console.log(data)});
  }
    
  //Animation Functions
  uploadNext() {
    this.showTimeBox = true;
    this.allowImageMargin = true;
    this.getImgMarginTop();
    this.logoStyle = (this.logoStyle === 'logoBefore' ? 'logoAfter' : 'logoBefore');
    this.hideUpload = (this.hideUpload === 'hideUploadBefore' ? 'hideUploadAfter' : 'hideUploadBefore');
    this.timeBox = (this.timeBox === 'timeBefore' ? 'timeAfter' : 'timeBefore');    
  }
  
  hideLogin() {
    this.loginStyle = (this.loginStyle === 'loginBefore' ? 'loginAfter' : 'loginBefore');
    this.uploadState = (this.uploadState === 'uploadBefore' ? 'uploadAfter' : 'uploadBefore');
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


  //Gedanken machen bei welchen Bedingungen nur das eine Ende abgefragt wird, nicht alle.... 
  //Mit Berni ausmachen wie das Json aussehen soll, wenn der Film endet mit "Ja" gedrückt würde
  endTrue() {

  }
}
