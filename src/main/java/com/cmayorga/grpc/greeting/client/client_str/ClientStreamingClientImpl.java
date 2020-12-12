package com.cmayorga.grpc.greeting.client.client_str;

import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ClientStreamingClientImpl {

    public void run(ManagedChannel channel) {

        // Create an asynchronous client
        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver = asyncClient
                .longGreet(new StreamObserver<LongGreetResponse>() {

            @Override
            public void onNext(LongGreetResponse value) {
                // Response from the server
                System.out.println("Received a response from the server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // Get an error from the server
            }

            @Override
            public void onCompleted() {
                // The server is done sending us data
                System.out.println("Server has completed sending us something");
                latch.countDown();
            }
        });

        System.out.println("Sending message 1");
        requestObserver.onNext(LongGreetRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Carlos").build())
                .build());

        System.out.println("Sending message 2");
        requestObserver.onNext(LongGreetRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Javier").build())
                .build());

        System.out.println("Sending message 3");
        requestObserver.onNext(LongGreetRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Mayorga").build())
                .build());

        // We tell the server that the client is done sending data
        requestObserver.onCompleted();

        try {
            latch.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
}
