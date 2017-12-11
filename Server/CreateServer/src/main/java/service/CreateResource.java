package service;

import dto.Linkcode;
import entity.Game;
import entity.Interaction;
import entity.Player;
import entity.Project;
import entity.Video;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import repository.GameFacade;
import repository.InteractionFacade;
import repository.PlayerFacade;
import repository.ProjectFacade;
import repository.VideoFacade;
import websocket.DeviceConnection;
import websocket.WebSocketCoreHolder;

@Path("create")
public class CreateResource {

    @Context
    private UriInfo context;

    @Inject
    GameFacade gameFacade;

    @Inject
    ProjectFacade projectRepository;

    @Inject
    PlayerFacade playerRepository;

    @Inject
    InteractionFacade interactionRepository;

    @Inject
    VideoFacade videoRepository;

    @Inject
    WebSocketCoreHolder wsHolder;

    public CreateResource() {

    }

    @GET
    @Path("initTestData")
    public void insertExamples() throws MalformedURLException {

        Player testPlayer = new Player();
        Interaction testInteraction = new Interaction();
        Video testVideo = new Video();

        Project testProject = new Project("Database Test Project",
                new URL("http://localhost:8080/test/project/url"),
                true,
                testPlayer,
                testVideo,
                new ArrayList<>(
                        Arrays.asList(testInteraction)));

        projectRepository.create(testProject);

    }

    @GET
    @Path("/initTestUser")
    public void insertTestUser() {
        playerRepository.create(new Player("email@test.at", "email"));
        playerRepository.create(new Player("email@testtest.at", "emailtest"));
        playerRepository.create(new Player("email@testtesttest.at", "emailtesttest"));
    }

    @GET
    @Path("/getAllUser")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> getAllUser() {
        return playerRepository.findAll();
    }

    @POST
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(Player player) {
        for (Player p : playerRepository.findAll()) {
            if (p.getEmail().equalsIgnoreCase(player.getEmail())) {
                return "false";
            }
        }
        playerRepository.create(player);
        return "true";
    }

    // 1.) APPLE-TV: neues Game erstellen
    @POST
    @Path("/newGame")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Linkcode newGame() {
        Game g = new Game();
        gameFacade.create(g);

        return new Linkcode(g.getLinkcode());
    }

    // 2.) ANDROID: Linkcode vergleichen
    @GET
    @Path("/linkcode/{link:[0-9][0-9]*}")
    public String compareLinkcode(@PathParam("link") final String linkcode) {
        for (Game g : gameFacade.findAll()) {
            if (g.getLinkcode().equals(linkcode)) {
                return "true";
            }
        }
        return "false";
    }

    // 3.) ANDROID: Projekte auflisten
    // 4) APPLE-TV: Interaktions-Aufruf
    @POST
    @Path("/interactionCheck/{id}")
    public String interactionCheck(@PathParam("id") final String id, String message) {
        JsonObject request;
        try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
            request = jsonReader.readObject();
        }
        String method = request.getString("method", "");
        String timestampTo = request.getString("timestampTo", "");
        for (DeviceConnection dc : wsHolder.getConnections()) {
            if (dc.getLinkcode().equalsIgnoreCase(id)) {
                dc.getSessionIP().getAsyncRemote().sendText("methodname:" + method + "::" + timestampTo);
                dc.setStreaming(false);
            }
        }
        return "" + wsHolder.getConnections().size();
    }

    // 4.1) ANDROID: Interaktion gemacht
    @POST
    @Path("/madeDecision/{id}")
    public String interactionCheck(@PathParam("id") final int id, String message) {
        JsonObject request;
        try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
            request = jsonReader.readObject();
        }
        String sekunde = request.getString("timestamp", "");
        for (DeviceConnection dc : wsHolder.getConnections()) {
            if (dc.getGameID() == id) {
                dc.getSessionTV().getAsyncRemote().sendText("timestamp:" + sekunde);
                dc.setStreaming(true);
            }
        }
        return "" + wsHolder.getConnections().size();
    }
}
