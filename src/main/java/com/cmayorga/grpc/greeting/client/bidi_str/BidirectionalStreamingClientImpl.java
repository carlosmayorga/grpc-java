package com.cmayorga.grpc.greeting.client.bidi_str;

import com.proto.greet.GreetEveryoneRequest;
import com.proto.greet.GreetEveryoneResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BidirectionalStreamingClientImpl {

    public void run(ManagedChannel channel) {

        // Create an asynchronous client
        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GreetEveryoneRequest> requestObserver = asyncClient
                .greetEveryone(new StreamObserver<GreetEveryoneResponse>() {

                    @Override
                    public void onNext(GreetEveryoneResponse value) {
                        System.out.println("Response from server BiDi " + value.getResult());
                    }

                    @Override
                    public void onError(Throwable t) {
                        latch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Server is done sending data");
                        latch.countDown();
                    }
                });

        // We send four message to the server
        Arrays.asList("Carlos", "Javier", "Mayorga").forEach(
                name -> {
                    System.out.println("Sending " + name);
                    requestObserver.onNext(GreetEveryoneRequest
                            .newBuilder()
                            .setGreeting(Greeting.newBuilder().setFirstName(name))
                            .build());

                    // To show that is really streaming
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        requestObserver.onCompleted();

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
