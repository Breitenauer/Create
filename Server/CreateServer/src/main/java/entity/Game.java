package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameId;
    
    private String linkcode;
    private Project actProject;
    private Player actPlayer;
    
    public Game() {
        this.linkcode = generateLinkcode();
    }

    public Game(Project actProject, Player actPlayer) {

    }

    public Project getActProject() {
        return actProject;
    }

    public void setActProject(Project actProject) {
        this.actProject = actProject;
    }

    public Player getActPlayer() {
        return actPlayer;
    }

    public void setActPlayer(Player actPlayer) {
        this.actPlayer = actPlayer;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getLinkcode() {
        return linkcode;
    }

    public void setLinkcode(String linkcode) {
        this.linkcode = linkcode;
    }

    private String generateLinkcode() {
        String linkcode = "";
        for (int i = 0; i < 4; i++) {
            linkcode += Integer.valueOf((int)(Math.random() * 10)).toString();
        }
        return linkcode;
    }
    
}
