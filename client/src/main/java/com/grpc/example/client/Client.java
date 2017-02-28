package com.grpc.example.client;

import com.grpc.example.Greet;

import java.util.logging.Logger;

/**
 * @author shettyh
 * @version 1.0
 */
public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            LOG.info(
                "Hostname,port and messgae are required arguments. Like [hostname port message]");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String messageString = args[2];

        try (GreetClient client = new GreetClient(host, port)) {
            Greet.GreetMessage message =
                Greet.GreetMessage.newBuilder().setGreeting(messageString).build();
            client.sendGreeting(message);
        }

        LOG.info("Done");
    }
}
