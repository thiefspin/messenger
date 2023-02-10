package com.thiefspin.messenger.server;

import java.io.IOException;

public interface TcpServer {
    void start();

    void stop() throws IOException;
}
