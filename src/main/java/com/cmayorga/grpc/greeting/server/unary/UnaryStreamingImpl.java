package com.cmayorga.grpc.greeting.server.unary;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class UnaryStreamingImpl {

    public void unaryImpl(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // Extract the fields we need
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

        // Create the response
        String result = "Hello" + firstName;

        GreetResponse response = GreetResponse
                .newBuilder()
                .setResult(result)
                .build();

        // Send the response
        responseObserver.onNext(response);

        // Complete the RPC call
        responseObserver.onCompleted();
    }
}
