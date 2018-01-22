export class Player {
    public email: string;
    public password: string;
    public playerId:string;

    constructor(playerid:string, email:string, password:string){
        this.email = email;
        this.password = password;
        this.playerId = playerid;
    }

}