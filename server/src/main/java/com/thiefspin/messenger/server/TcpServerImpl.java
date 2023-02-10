package com.thiefspin.messenger.server;

import com.thiefspin.messenger.context.ApplicationContext;
import com.thiefspin.messenger.executor.WorkerPool;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServerImpl implements TcpServer {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final WorkerPool pool = new WorkerPool();

    private final ApplicationContext context;
    private ServerSocket serverSocket;

    public TcpServerImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void start() {
        logger.info("Initialising server...");
        try {
            serverSocket = new ServerSocket(context.getServerPort());
            logger.info("Server started at " + serverSocket.getLocalPort());
            while (true) {
                pool.allocate(new TcpSocketClient(serverSocket.accept()));
            }
        } catch (IOException e) {
            logger.error("Stopping Tcp Server...", e);
        }
    }

    @Override
    public void stop() throws IOException {
        serverSocket.close();
    }
}
