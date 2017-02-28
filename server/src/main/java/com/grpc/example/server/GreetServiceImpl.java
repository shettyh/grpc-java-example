package com.grpc.example.server;

import com.google.protobuf.Empty;
import com.grpc.example.Greet;
import com.grpc.example.service.GreetGrpc;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

/**
 * @author shettyh
 * @version 1.0
 */
public class GreetServiceImpl extends GreetGrpc.GreetImplBase {

    private final Logger LOG = Logger.getLogger(GreetServiceImpl.class.getName());

    @Override
    public void sendMessage(Greet.GreetMessage request, StreamObserver<Empty> responseObserver) {
        LOG.info("Go the message :" + request.getGreeting());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
