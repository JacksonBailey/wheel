package dev.jacksonbailey.wheel.vexillum.server;

import static org.junit.jupiter.api.Assertions.*;

import dev.jacksonbailey.wheel.vexillum.server.VexillumServer;
import dev.jacksonbailey.wheel.vexillum.server.VexillumServer.GreeterImpl;
import dev.jacksonbailey.wheel.vexillum.api.GreeterGrpc;
import dev.jacksonbailey.wheel.vexillum.api.HelloReply;
import dev.jacksonbailey.wheel.vexillum.api.HelloRequest;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link VexillumServer}.
 * For demonstrating how to write gRPC unit test only.
 * Not intended to provide a high code coverage or to test every major usecase.
 * <p>
 * directExecutor() makes it easier to have deterministic tests.
 */
public class VexillumServerTest {

  @Rule
  public GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  /**
   * To test the server, make calls with a real stub using the in-process channel, and verify
   * behaviors or state changes from the client side.
   */
  @Test
  public void greeterImpl_replyMessage() throws Exception {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();

    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(InProcessServerBuilder
        .forName(serverName).directExecutor().addService(new GreeterImpl()).build().start());

    GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(
        // Create a client channel and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));


    HelloReply reply =
        blockingStub.sayHello(HelloRequest.newBuilder().setName( "test name").build());

    assertEquals("Hello test name", reply.getMessage());
  }

}
