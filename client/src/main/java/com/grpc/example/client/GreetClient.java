package com.grpc.example.client;

import com.grpc.example.Greet;
import com.grpc.example.service.GreetGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shettyh
 * @version 1.0
 */
public class GreetClient implements AutoCloseable {

    private final Logger LOG = Logger.getLogger(GreetClient.class.getName());
    private ManagedChannel _Channel;
    private GreetGrpc.GreetBlockingStub _Stub;


    public GreetClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port));
    }

    public GreetClient(ManagedChannelBuilder<?> channelBuilder) {
        _Channel = channelBuilder.usePlaintext(true).build();
        _Stub = GreetGrpc.newBlockingStub(_Channel);
        LOG.info("Connected to the server.");
    }


    public void sendGreeting(Greet.GreetMessage greetMessage) {
        try {
            _Stub.sendMessage(greetMessage);
            LOG.info("Message sent");
        } catch (StatusRuntimeException sre) {
            LOG.log(Level.WARNING, "RPC failed: [0]", sre.getStatus());
        }
    }

    @Override public void close() throws Exception {
        if (_Channel != null)
            _Channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
