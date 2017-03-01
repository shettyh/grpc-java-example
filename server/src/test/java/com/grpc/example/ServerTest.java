package com.grpc.example;

import com.google.protobuf.Empty;
import com.grpc.example.server.GreetServiceImpl;
import com.grpc.example.service.GreetGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author shettyh
 * @version 1.0
 */
@Test public class ServerTest {

    private final String SERVER_NAME = "in-process for " + ServerTest.class;
    private final Server inProcessServer =
        InProcessServerBuilder.forName(SERVER_NAME).addService(new GreetServiceImpl())
            .directExecutor().build();
    private final ManagedChannel inProcessChannel =
        InProcessChannelBuilder.forName(SERVER_NAME).directExecutor().build();

    @BeforeTest public void init() throws IOException {
        inProcessServer.start();
    }

    @AfterTest public void tearDown() {
        inProcessServer.shutdown();
    }

    @Test public void greetSendMessage() {
        GreetGrpc.GreetBlockingStub stub = GreetGrpc.newBlockingStub(inProcessChannel);
        String message = "Hello";
        Empty empty =
            stub.sendMessage(Greet.GreetMessage.newBuilder().setGreeting(message).build());
    }
}
