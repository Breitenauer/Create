import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { trigger,state,style,transition,animate,keyframes } from '@angular/animations';

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
    //Left Right 
    public decisionsLR:string[] = ["Links", "Rechts"];
    //Up Down
    public decisionsUD:string[] = ["Oben", "Unten"];
    //Button
    public decisionsBtn:string[] = ["A", "B"];

    //Slider
    firstValue:number;
    selectedValue: string;
    
    subDecisions = [
      {value: 'lr', viewValue: 'Left or Right'},
      {value: 'ud', viewValue: 'Up or Down'},
      {value: 'ab', viewValue: 'A or B'}
    ];

  

  public n: number = 1;

  constructor(rest:RestService) {
    this.rest = rest;
    this.value = 0;
  }


  ngOnInit() {
    this.decisions.push({title: "Entscheidung 1", value: this.firstValue, isBtn: false, isLR: false, isUD:false});
    this.uploaded = true;
    /* erstes Request an Server 
    this.students.push(new Student(null, "", "", null, null, null));
    this.rest.findTeams().subscribe(data => {this.teams = data});

    */
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

  selectTeam(){
    /* response von Server
    this.selectedTeam = team;
    console.log("SELECTED TEAM: " + team.teamId);
    this.rest.findStudentsByTeam(this.selectedTeam.teamId).subscribe(data => {this.students = data ;});
    this.selectedStudent = new Student(null, "", "", null, null, null);
    this.update = false;
    this.nStd = true;
*/
  }

  //Timestamps
  createExpansion(dec:Decision) {
    this.decisions.forEach(element => {
      if(element.title == dec.title) {
        switch(this.selectedValue){
          case 'lr': element.isLR = true; element.isUD = false; element.isBtn = false; break;
          case 'ud': element.isUD = true; element.isLR = false; element.isBtn = false; break;
          case 'ab': element.isBtn = true; element.isLR = false; element.isUD = false; break;
        }
      }
    });
  }

  endFalse() {

    //Entscheidung 1 wird disabled
    //löschen Button einführen
    //Slider konfigurieren mit Zeit
    //Export Button darf nur möglich sein, wenn alle Entscheidungen fertig sind (wenn alle "Endet hier der Film" mit "Ja" beantwortet werden konnten)
    var count = this.decisions.length+1;
    this.decisions.push({title: "Entscheidung "+count, value: this.firstValue, isBtn: false, isLR: false, isUD:false});    
  }
}

export class Decision {
  public title: string;
  public value: number;
  public isLR: boolean;
  public isUD: boolean;
  public isBtn: boolean;
}