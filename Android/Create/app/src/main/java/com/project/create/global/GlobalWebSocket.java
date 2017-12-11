package com.project.create.global;

import android.content.Context;
import android.content.Intent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class GlobalWebSocket {

    private OkHttpClient client;
    public WebSocket ws;

    private Context context;

    private final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello, it's the Android-Device!");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {

            String methodname = null;

            if (text.contains("GAMEID")) {
                String[] data = text.split(":");
                Variables.gameId = data[1];
            }

            if (text.contains("methodname")) {
                String[] data = text.split("::");
                String[] methodData = data[0].split(":");
                methodname = methodData[1];
                if (data[1].contains(";")) {
                    String[] timestamps = data[1].split(";");
                    String[] time = timestamps[0].split(":");
                    Variables.timestamp1 = time[1];

                    time = timestamps[1].split(":");
                    Variables.timestamp2 = time[1];
                } else {
                    Variables.timestamp1 = data[1];
                    Variables.timestamp2 = null;
                }
                try {
                    System.out.println(methodname);
                    Class<?> c = Class.forName("com.project.create.interactions." + methodname);
                    context.startActivity(new Intent(context, c).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (ClassNotFoundException ignored) {
                    System.out.println(ignored);
                }
            }


        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            System.out.println("Closed:" + code);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.out.println(t.getMessage());
        }
    }

    public GlobalWebSocket(Context context) {
        this.context = context;
        client = new OkHttpClient();
        start();
    }

    public void start() {
        Request request = new Request.Builder().url("ws://vm18.htl-leonding.ac.at:8080/createws").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

}
