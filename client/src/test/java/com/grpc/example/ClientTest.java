package com.grpc.example;

import com.google.protobuf.Empty;
import com.grpc.example.client.GreetClient;
import com.grpc.example.service.GreetGrpc;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


/**
 * @author shettyh
 * @version 1.0
 */
@Test public class ClientTest {

    private final GreetGrpc.GreetImplBase serviceImpl = spy(new GreetGrpc.GreetImplBase() {
    });
    private Server fakeServer;
    private GreetClient client;

    @BeforeTest public void init() throws IOException {
        String serverName = "InProcess_" + ClientTest.class;
        fakeServer =
            InProcessServerBuilder.forName(serverName).addService(serviceImpl).directExecutor()
                .build().start();
        InProcessChannelBuilder inProcessServerBuilder =
            InProcessChannelBuilder.forName(serverName).directExecutor();
        client = new GreetClient(inProcessServerBuilder);
    }

    @AfterTest public void tearDown() throws Exception {
        client.close();
        fakeServer.shutdown();
    }


    @Test public void greetMessage() {
        ArgumentCaptor<Greet.GreetMessage> requestCoptor =
            ArgumentCaptor.forClass(Greet.GreetMessage.class);
        String message = "Hello";

        client.sendGreeting(Greet.GreetMessage.newBuilder().setGreeting(message).build());
        verify(serviceImpl)
            .sendMessage(requestCoptor.capture(), Mockito.<StreamObserver<Empty>>any());
        Assert.assertEquals(message, requestCoptor.getValue().getGreeting());

    }

}
