package dev.jacksonbailey.wheel.vexillum;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.jacksonbailey.wheel.vexillum.protos.GreeterGrpc;
import dev.jacksonbailey.wheel.vexillum.protos.HelloReply;
import dev.jacksonbailey.wheel.vexillum.protos.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;


/**
 * Unit tests for {@link VexillumClient}.
 * For demonstrating how to write gRPC unit test only.
 * Not intended to provide a high code coverage or to test every major usecase.
 * <p>
 * directExecutor() makes it easier to have deterministic tests.
 */
class VexillumClientTest {

  /**
   * This rule manages automatic graceful shutdown for the registered servers and channels at the
   * end of test.
   */
  @Rule
  public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  private final GreeterGrpc.GreeterImplBase serviceImpl =
      mock(GreeterGrpc.GreeterImplBase.class, delegatesTo(
          new GreeterGrpc.GreeterImplBase() {
            // By default the client will receive Status.UNIMPLEMENTED for all RPCs.
            // You might need to implement necessary behaviors for your test here, like this:
            //
            /*@Override
            public void sayHello(HelloRequest request, StreamObserver<HelloReply> respObserver) {
              respObserver.onNext(HelloReply.getDefaultInstance());
              respObserver.onCompleted();
            }*/
          }));

  private VexillumClient client;

  @BeforeEach
  public void beforeEach() throws Exception {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();

    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(InProcessServerBuilder
        .forName(serverName).directExecutor().addService(serviceImpl).build().start());

    // Create a client channel and register for automatic graceful shutdown.
    ManagedChannel channel = grpcCleanup.register(
        InProcessChannelBuilder.forName(serverName).directExecutor().build());

    // Create a HelloWorldClient using the in-process channel;
    client = new VexillumClient(channel);
  }

  /**
   * To test the client, call from the client against the fake server, and verify behaviors or state
   * changes from the server side.
   */
  @Test
  public void greet_messageDeliveredToServer() {
    var requestCaptor = ArgumentCaptor.forClass(HelloRequest.class);

    client.greet("test name");

    verify(serviceImpl).sayHello(any(), any());
    verify(serviceImpl).sayHello(requestCaptor.capture(), any());
    assertEquals("test name", requestCaptor.getValue().getName());
  }

}
