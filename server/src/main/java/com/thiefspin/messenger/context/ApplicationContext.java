package com.thiefspin.messenger.context;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;

@Getter
public class ApplicationContext {

    private final Config config = ConfigFactory.load();

    public int serverPort = getConfig().getInt("tcp.server.port");
}
