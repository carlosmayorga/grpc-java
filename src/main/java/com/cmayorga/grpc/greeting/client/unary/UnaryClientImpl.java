package com.cmayorga.grpc.greeting.client.unary;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;

public class UnaryClientImpl {
    public void run(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub greetingClient =
                GreetServiceGrpc.newBlockingStub(channel);
        // UNARY CLIENT
        // First define the object
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Carlos")
                .setLastName("Mayorga")
                .build();

        // second define the request
        GreetRequest greetRequest = GreetRequest
                .newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = greetingClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

    };
}
