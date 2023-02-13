package dev.jacksonbailey.wheel.vexillum.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dev.jacksonbailey.wheel.vexillum.api.Flag;
import dev.jacksonbailey.wheel.vexillum.api.FlagServiceGrpc;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateRequest;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateRequest;
import dev.jacksonbailey.wheel.vexillum.server.VexillumServer.FlagServiceImpl;
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
  public void flagServiceImpl_replyMessage() throws Exception {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();

    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(InProcessServerBuilder
        .forName(serverName).directExecutor().addService(new FlagServiceImpl()).build().start());

    FlagServiceGrpc.FlagServiceBlockingStub blockingStub = FlagServiceGrpc.newBlockingStub(
        // Create a client channel and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

    var flagName = "test name";

    var expectedFlag = Flag.newBuilder().setName(flagName).setState(true).build();

    var setReply = blockingStub.setFlagState(
        SetFlagStateRequest.newBuilder()
                           .setNewFlag(expectedFlag)
                           .build()
    );

    assertFalse(setReply.hasPreviousFlag());

    var getReply = blockingStub.getFlagState(
        GetFlagStateRequest.newBuilder().setName(flagName).build()
    );

    assertEquals(expectedFlag, getReply.getCurrentFlag());
  }

}
