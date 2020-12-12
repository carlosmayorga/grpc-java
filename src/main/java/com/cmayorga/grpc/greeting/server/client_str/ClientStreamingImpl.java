package com.cmayorga.grpc.greeting.server.client_str;

import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.stub.StreamObserver;

public class ClientStreamingImpl {

    public StreamObserver<LongGreetRequest> clientImpl(StreamObserver<LongGreetResponse> responseObserver) {
        StreamObserver<LongGreetRequest> requestObserver = new StreamObserver<LongGreetRequest>() {
            String result = "";

            @Override
            public void onNext(LongGreetRequest value) {
                // Client sends a message
                result += "Hello " + value.getGreeting().getFirstName();
            }

            @Override
            public void onError(Throwable t) {
                // Client send a error
            }

            @Override
            public void onCompleted() {
                // Client is done
                responseObserver.onNext(LongGreetResponse
                        .newBuilder()
                        .setResult(result)
                        .build()
                );
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }
}
