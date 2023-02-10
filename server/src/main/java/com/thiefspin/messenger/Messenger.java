package com.thiefspin.messenger;

import com.thiefspin.messenger.context.ApplicationContext;
import com.thiefspin.messenger.server.TcpServer;
import com.thiefspin.messenger.server.TcpServerImpl;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Messenger {

    private static final Logger logger = Logger.getLogger("Messenger");
    private static final ApplicationContext context = new ApplicationContext();

    public static void main(String[] args) {
        logger.info("Starting server...");
        TcpServer server = new TcpServerImpl(context);
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.stop();
            } catch (IOException e) {
                logger.error("Failed to stop server!. Cause " + e.getMessage());
            }
        }));
    }
}
