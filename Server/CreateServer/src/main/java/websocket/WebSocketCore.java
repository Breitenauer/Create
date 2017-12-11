package websocket;

import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.transaction.Transactional;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import repository.GameFacade;

@ServerEndpoint("/createws")
@Stateless
public class WebSocketCore {

    @Inject
    GameFacade gameFacade;
    
    @Inject
    WebSocketCoreHolder wsHolder;

    public WebSocketCore() {
        
    }

    @OnOpen
    public void onOpen(Session session) {
        wsHolder.getSessions().add(session);
    }

    @OnMessage
    @SuppressWarnings("null")
    public void onMessage(Session session, String message) {
        JsonObject request;
        try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
            request = jsonReader.readObject();
        }

        String device = request.getString("device", "");
        String linkcode = request.getString("linkcode", "");
        String start = request.getString("start", "");

        System.out.println(message);
        
        // {"linkcode":"7557","device":"TV"}
        if (device.equals("TV")) {
            if (!linkcode.isEmpty()) {
                DeviceConnection dc = new DeviceConnection(gameFacade.getGameOverLinkcode(linkcode).getGameId(), linkcode, session);
                wsHolder.getConnections().add(dc);
                session.getAsyncRemote().sendText("GAMEID:" + Integer.valueOf(dc.getGameID()).toString());
            }
        } else if (device.equals("IP")) {
            //{"linkcode":"7557","device":"IP"}
            for (DeviceConnection dc : wsHolder.getConnections()) {
                if (dc.getLinkcode().equals(linkcode) && !dc.isStreaming() && start.isEmpty()) {
                    dc.setSessionIP(session);
                    session.getAsyncRemote().sendText("GAMEID:" + Integer.valueOf(dc.getGameID()).toString());
                    //{"linkcode":"7557","device":"IP","start":"jo"}
                } else if (dc.getLinkcode().equals(linkcode) && !dc.isStreaming() && !start.isEmpty()) {
                    dc.setStreaming(true);
                    dc.getSessionTV().getAsyncRemote().sendText("start");
                }
            }
        }

    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        wsHolder.getSessions().remove(session);
    }

}
