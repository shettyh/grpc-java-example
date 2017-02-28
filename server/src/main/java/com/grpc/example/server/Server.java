package com.grpc.example.server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author shettyh
 * @version 1.0
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            LOG.info("Port number is needed to run the server.");
            System.exit(1);
        }

        Service service = new Service(Integer.parseInt(args[0]));
        service.start();
        service.waitForCompletion();
    }
}
