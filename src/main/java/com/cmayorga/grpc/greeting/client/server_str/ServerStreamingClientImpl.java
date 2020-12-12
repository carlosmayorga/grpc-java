package com.cmayorga.grpc.greeting.client.server_str;

import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;

public class ServerStreamingClientImpl {

    public void run(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub greetingClient =
                GreetServiceGrpc.newBlockingStub(channel);

        // Prepare the request
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Carlos"))
                .build();

        // Stream the response (in a blocking manner)
        greetingClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
                });
    };
}
