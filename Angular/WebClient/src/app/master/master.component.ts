import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})
export class MasterComponent implements OnInit {
  private rest;

  /** Progress Bar **/
  color = 'primary';
  mode = 'determinate';
  value = 50;
  bufferValue = 75;
  
  constructor(rest:RestService) {
    this.rest = rest;
  }

  ngOnInit() {
    /* erstes Request an Server 
    this.students.push(new Student(null, "", "", null, null, null));
    this.rest.findTeams().subscribe(data => {this.teams = data});

    */
  }

  uploadFile(event) {
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


}
