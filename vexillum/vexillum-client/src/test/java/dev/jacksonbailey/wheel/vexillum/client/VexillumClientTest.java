package dev.jacksonbailey.wheel.vexillum.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.jacksonbailey.wheel.vexillum.api.FlagServiceGrpc;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateRequest;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateRequest;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


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

  private final List<Object> list = new ArrayList<>(); // Bargain-bin argument captor

  private final FlagServiceGrpc.FlagServiceImplBase serviceImpl =
      mock(FlagServiceGrpc.FlagServiceImplBase.class, delegatesTo(
          new FlagServiceGrpc.FlagServiceImplBase() {
            @Override
            public void getFlagState(GetFlagStateRequest request,
                StreamObserver<GetFlagStateReply> respObserver) {
              list.add(request);
              respObserver.onNext(GetFlagStateReply.getDefaultInstance());
              respObserver.onCompleted();
            }
            @Override
            public void setFlagState(SetFlagStateRequest request,
                StreamObserver<SetFlagStateReply> respObserver) {
              list.add(request);
              respObserver.onNext(SetFlagStateReply.getDefaultInstance());
              respObserver.onCompleted();
            }
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
  public void getFlag_messageDeliveredToServer() {
    // TODO For some reason mockito no longer understands that the mock is being called
    //var requestCaptor = ArgumentCaptor.forClass(GetFlagStateRequest.class);

    client.getFlag("test name");

    assertEquals(1, list.size());
    assertEquals("test name", ((GetFlagStateRequest) list.get(0)).getName());
    //verify(serviceImpl).getFlagState(requestCaptor.capture(), any());
    //assertEquals("test name", requestCaptor.getValue().getName());
  }

  /**
   * To test the client, call from the client against the fake server, and verify behaviors or state
   * changes from the server side.
   */
  @Test
  public void setFlag_messageDeliveredToServer() {
    // TODO For some reason mockito no longer understands that the mock is being called
    //var requestCaptor = ArgumentCaptor.forClass(SetFlagStateRequest.class);

    client.setFlag("test name", true);

    assertEquals(1, list.size());
    assertEquals("test name", ((SetFlagStateRequest) list.get(0)).getNewFlag().getName());
    assertTrue(((SetFlagStateRequest) list.get(0)).getNewFlag().getState());
    //verify(serviceImpl).setFlagState(requestCaptor.capture(), any());
    //assertEquals("test name", requestCaptor.getValue().getNewFlag().getName());
    //assertTrue(requestCaptor.getValue().getNewFlag().getState());
  }

}
