import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
/*import { Student } from './master/student';*/

@Injectable()
export class RestService {

  private http;
  
    constructor(http: HttpClient) {
      this.http = http;
    }

    uploadFile(file) {
      let formData: FormData = new FormData();
      formData.append('file', file, file.name);
      this.http.post("localhost:4200/videos", formData);
      console.log("sucessfully uploaded");
    }
  
  /* GET 
    findStudentsByTeam(teamid:string) {
      return this.http.get("http://localhost:8080/server/api/rest/findSbyT/"+teamid);
    }

  /* PUT 
    editStudent(student:Student){
      return this.http.put("http://localhost:8080/server/api/rest/updateS/"+student.studentId, student);
    }
    
  /* DELETE 
    deleteStudent(student:Student){
      return this.http.delete("http://localhost:8080/server/api/rest/deleteS/"+student.studentId);
    }

    */
}
