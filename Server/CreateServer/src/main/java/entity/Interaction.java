package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Interaction implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int interactionId;
    
    private String methodName;
    private Boolean doesVideoEnd;
    
    private int timestamp, timestampToA, timestampToB;

    public Interaction() {
    }

    public Interaction(String methodName, Boolean doesVideoEnd, int timestamp, int timestampToA, int timestampToB) {
        this.methodName = methodName;
        this.doesVideoEnd = doesVideoEnd;
        this.timestamp = timestamp;
        this.timestampToA = timestampToA;
        this.timestampToB = timestampToB;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getDoesVideoEnd() {
        return doesVideoEnd;
    }

    public void setDoesVideoEnd(Boolean doesVideoEnd) {
        this.doesVideoEnd = doesVideoEnd;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimestampToA() {
        return timestampToA;
    }

    public void setTimestampToA(int timestampToA) {
        this.timestampToA = timestampToA;
    }

    public int getTimestampToB() {
        return timestampToB;
    }

    public void setTimestampToB(int timestampToB) {
        this.timestampToB = timestampToB;
    }
    
}
