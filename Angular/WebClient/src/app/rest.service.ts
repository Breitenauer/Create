import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Decision } from "./decision2";
import { Player } from "./player";
import { Player2 } from "./player2";



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
    
    // POST
    uploadInteractions(decisions:Decision[]){
      console.log("lets post!")
      //return this.http.post("http://localhost:8080/server/api/rest/test", decisions);
      return this.http.post("http://vm18.htl-leonding.ac.at:8080/api/create/createProject/", decisions);
    }

    createPlayer(email:string, password:string){
      return this.http.post("http://vm18.htl-leonding.ac.at:8080/api/create/createUser", new Player2(email,password));
    }

    checkPlayer() {
      return this.http.get("http://vm18.htl-leonding.ac.at:8080/api/create/getAllUser");
    }
    
}
