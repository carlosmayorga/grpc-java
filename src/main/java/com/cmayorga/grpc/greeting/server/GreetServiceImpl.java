package com.cmayorga.grpc.greeting.server;

import com.cmayorga.grpc.greeting.server.bidi_str.BidirectionalStreamingImpl;
import com.cmayorga.grpc.greeting.server.client_str.ClientStreamingImpl;
import com.cmayorga.grpc.greeting.server.server_str.ServerStreamingImpl;
import com.cmayorga.grpc.greeting.server.unary.UnaryStreamingImpl;
import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        UnaryStreamingImpl x = new UnaryStreamingImpl();
        x.unaryImpl(request, responseObserver);
    }


    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        ServerStreamingImpl x = new ServerStreamingImpl();
        x.serverImpl(request, responseObserver);
    }


    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {
        ClientStreamingImpl x = new ClientStreamingImpl();
        return x.clientImpl(responseObserver);
    }


    @Override
    public StreamObserver<GreetEveryoneRequest> greetEveryone(StreamObserver<GreetEveryoneResponse> responseObserver) {

        BidirectionalStreamingImpl x = new BidirectionalStreamingImpl();
        return x.bidiImpl(responseObserver);
    }

}
