import { Decision } from "./decision2";
import { Player2 } from "./player2";

export class Project {
    public description: string;
    public date: string;
    public url:string;
    public isPublic:string;
    public player:Player2;
    public thumbnail:string;
    public decisions:Array<Decision>;

    constructor(description:string, date:string, url:string, isPublic:string, player:Player2, thumbnail:string, decisions:Array<Decision>){
        this.description = description;
        this.date = date;
        this.url = url;
        this.isPublic = isPublic;
        this.player = player;
        this.thumbnail = thumbnail; 
        this.decisions = decisions;
    }

}