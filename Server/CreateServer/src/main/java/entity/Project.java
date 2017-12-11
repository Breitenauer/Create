package entity;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    
    private String description;
    private URL thumbnailUrl;
    private boolean isPublic;
         
    @OneToOne (cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Player player;
    
    @OneToOne (cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Video video;
    
    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Interaction> interactions;

    public Project() {
        
    }

    public Project(String description, URL thumbnailUrl, boolean isPublic, Player player, Video video, List<Interaction> interactions) {
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.isPublic = isPublic;
        this.player = player;
        this.video = video;
        this.interactions = interactions;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(URL thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
    
}
