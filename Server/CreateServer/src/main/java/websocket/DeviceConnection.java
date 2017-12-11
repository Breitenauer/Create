package websocket;

import javax.websocket.Session;

public class DeviceConnection {

    private int gameID;
    private String linkcode;
    private Session sessionTV;
    private Session sessionIP;
    private boolean isStreaming;

    public DeviceConnection() {

    }

    public DeviceConnection(int gameID, String linkcode, Session sessionTV) {
        this.gameID = gameID;
        this.sessionTV = sessionTV;
        this.linkcode = linkcode;
        this.isStreaming = false;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getLinkcode() {
        return linkcode;
    }

    public void setLinkcode(String linkcode) {
        this.linkcode = linkcode;
    }

    public Session getSessionTV() {
        return sessionTV;
    }

    public void setSessionTV(Session sessionTV) {
        this.sessionTV = sessionTV;
    }

    public Session getSessionIP() {
        return sessionIP;
    }

    public void setSessionIP(Session sessionIP) {
        this.sessionIP = sessionIP;
    }

    public boolean isStreaming() {
        return isStreaming;
    }

    public void setStreaming(boolean isStreaming) {
        this.isStreaming = isStreaming;
    }

}
