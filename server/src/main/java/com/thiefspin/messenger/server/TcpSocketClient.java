package com.thiefspin.messenger.server;

import com.thiefspin.messenger.common.signals.ClientSignals;
import com.thiefspin.messenger.common.message.MessageResponse;
import com.thiefspin.messenger.handler.MessageHandler;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.RecursiveTask;

public class TcpSocketClient extends RecursiveTask<MessageResponse> {

    private PrintWriter out;
    private BufferedReader in;
    private final Socket clientSocket;
    @Getter
    private Boolean active;

    public TcpSocketClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        active = true;
    }

    public void run() {
        try {
            handleSocketCommunications();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSocketCommunications() throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals(ClientSignals.DISCONNECT.getName())) {
                stop();
                break;
            } else {
                new MessageHandler(out, inputLine).handle();
            }
        }
    }

    private void stop() throws IOException {
        active = false;
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    protected MessageResponse compute() {
        run();
        return null;
    }
}
