package com.grpc.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author shettyh
 * @version 1.0
 */
public class Service {
    private final Logger LOG = Logger.getLogger(Service.class.getName());
    private Server _Server;
    private final int _port;

    public Service(int port) {
        this._port = port;
    }

    public void start() throws IOException {
        LOG.info("Starting the service on port [" + _port + "] ...");
        _Server = ServerBuilder.forPort(_port).addService(new GreetServiceImpl()).build();
        _Server.start();
        LOG.info("Started service.");
    }

    public void waitForCompletion() throws InterruptedException {
        if (_Server != null)
            _Server.awaitTermination();
    }

    public void stop() {
        if (_Server != null)
            _Server.shutdown();
    }

}
