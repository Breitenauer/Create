package websocket;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.websocket.Session;

@Named
@ApplicationScoped
public class WebSocketCoreHolder {
	
    private ArrayList<DeviceConnection> connections = new ArrayList<DeviceConnection>();
    private ArrayList<Session> sessions = new ArrayList<Session>();
	
    public WebSocketCoreHolder() {

    }

    public ArrayList<DeviceConnection> getConnections() {
	return connections;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
		
}
