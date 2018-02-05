import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Decision } from "./decision2";
import { Player } from "./player";
import { Player2 } from "./player2";
import { Project } from "./project";



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
    uploadInteractions(description:string,isPublic:string,player:Player2,decisions:Decision[]){
      console.log("lets post!")
      //return this.http.post("http://localhost:8080/server/api/rest/test", decisions);
      return this.http.post("http://vm18.htl-leonding.ac.at:8080/api/create/createProject", new Project(description, "05.02.2018", "http://vm18.htl-leonding.ac.at:8080/images/thumbnail.jpg", isPublic, player, "{'Linz-Runde':'http://vm18.htl-leonding.ac.at:8080/videos/linz.mp4'}", decisions));
    }

    createPlayer(email:string, password:string){
      return this.http.post("http://vm18.htl-leonding.ac.at:8080/api/create/createUser", new Player2(email,password));
    }

    checkPlayer() {
      return this.http.get("http://vm18.htl-leonding.ac.at:8080/api/create/getAllUser");
    }
    
}
