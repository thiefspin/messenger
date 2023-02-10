package com.thiefspin.messenger.executor;

import com.thiefspin.messenger.server.TcpSocketClient;
import org.apache.log4j.Logger;

import java.util.concurrent.ForkJoinPool;

public class WorkerPool {

    private Logger logger = Logger.getLogger(this.getClass());

    private final int CPUCores = Runtime.getRuntime().availableProcessors();

    private final ForkJoinPool pool = new ForkJoinPool(CPUCores);

    public void allocate(TcpSocketClient client) {
        pool.execute(client);
    }

    public WorkerPool() {
        logger.info("Initialised worker pool with " + CPUCores + " CPU Cores");
    }
}
