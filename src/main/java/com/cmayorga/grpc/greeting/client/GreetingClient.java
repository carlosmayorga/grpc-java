package com.cmayorga.grpc.greeting.client;

import com.cmayorga.grpc.greeting.client.bidi_str.BidirectionalStreamingClientImpl;
import com.cmayorga.grpc.greeting.client.client_str.ClientStreamingClientImpl;
import com.cmayorga.grpc.greeting.client.server_str.ServerStreamingClientImpl;
import com.cmayorga.grpc.greeting.client.unary.UnaryClientImpl;
import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClient {
    ManagedChannel channel;

    public static void main(String[] args) {
        System.out.println("Hello from GoogleRPC Client");
        new GreetingClient().run();
        System.out.println("Creating Stub");
    }

    private void run() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        new UnaryClientImpl().run(channel);
        //new ServerStreamingClientImpl().run(channel);
        //new ClientStreamingClientImpl().run(channel);
        //new BidirectionalStreamingClientImpl().run(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

}

