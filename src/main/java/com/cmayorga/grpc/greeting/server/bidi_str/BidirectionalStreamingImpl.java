package com.cmayorga.grpc.greeting.server.bidi_str;

import com.proto.greet.GreetEveryoneRequest;
import com.proto.greet.GreetEveryoneResponse;
import io.grpc.stub.StreamObserver;

public class BidirectionalStreamingImpl {

    public StreamObserver<GreetEveryoneRequest> bidiImpl(StreamObserver<GreetEveryoneResponse> responseObserver) {
        StreamObserver<GreetEveryoneRequest> requestObserver = new StreamObserver<GreetEveryoneRequest>() {
            int i = 0;
            @Override
            public void onNext(GreetEveryoneRequest value) {
                i+=1;
                System.out.println("Print on server ===> Receiving data "+ i);
                String response = i + " " + value.getGreeting().getFirstName();
                GreetEveryoneResponse greetEveryoneResponse = GreetEveryoneResponse
                        .newBuilder()
                        .setResult(response)
                        .build();

                responseObserver.onNext(greetEveryoneResponse);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
        return requestObserver;
    }
}
