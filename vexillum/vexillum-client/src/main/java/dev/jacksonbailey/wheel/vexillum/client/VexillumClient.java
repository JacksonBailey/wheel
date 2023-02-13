package dev.jacksonbailey.wheel.vexillum.client;

import dev.jacksonbailey.wheel.vexillum.api.Flag;
import dev.jacksonbailey.wheel.vexillum.api.FlagServiceGrpc;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateRequest;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple client that requests a flag from the {@code VexillumServer}.
 */
public class VexillumClient {

  private static final Logger log = LoggerFactory.getLogger(VexillumClient.class);

  private final FlagServiceGrpc.FlagServiceBlockingStub blockingStub;

  /**
   * Construct client for accessing Vexillum server using the existing channel.
   */
  public VexillumClient(Channel channel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
    blockingStub = FlagServiceGrpc.newBlockingStub(channel);
  }

  /**
   * Get a flag's state.
   */
  public void getFlag(@NotNull String name) {
    log.info("Will try to get flag {} ...", name);
    var request = GetFlagStateRequest.newBuilder().setName(name).build();
    GetFlagStateReply response;
    try {
      response = blockingStub.getFlagState(request);
    } catch (StatusRuntimeException e) {
      log.error("RPC failed: {}", e.getStatus(), e);
      return;
    }
    log.info("Current flag state: {}", response.getCurrentFlag());
  }

  /**
   * Set a flag's state.
   */
  public void setFlag(@NotNull String name, boolean state) {
    log.info("Will try to set flag {} to {} ...", name, state);
    var request = SetFlagStateRequest.newBuilder()
                                     .setNewFlag(
                                         Flag.newBuilder()
                                             .setName(name)
                                             .setState(state)
                                             .build()
                                     )
                                     .build();
    SetFlagStateReply response;
    try {
      response = blockingStub.setFlagState(request);
    } catch (StatusRuntimeException e) {
      log.error("RPC failed: {}", e.getStatus(), e);
      return;
    }
    log.info("Previous flag state: {}", response.getPreviousFlag());
  }

  /**
   * Flag server. If provided, the first element of {@code args} is the name to use in the flag
   * name. The second argument is the target server.
   */
  public static void main(String[] args) throws Exception {
    String flagName = "foo";
    // Access a service running on the local machine on port 50051
    String target = "localhost:50051";
    // Allow passing in the user and target strings as command line arguments
    if (args.length > 0) {
      if ("--help".equals(args[0])) {
        System.err.println("Usage: [name [target]]");
        System.err.println("");
        System.err.println("  name    The name you wish to be greeted by. Defaults to " + flagName);
        System.err.println("  target  The server to connect to. Defaults to " + target);
        System.exit(1);
      }
      flagName = args[0];
    }
    if (args.length > 1) {
      target = args[1];
    }

    // Create a communication channel to the server, known as a Channel. Channels are thread-safe
    // and reusable. It is common to create channels at the beginning of your application and reuse
    // them until the application shuts down.
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                                                  // Channels are secure by default (via SSL/TLS).
                                                  // For the example we disable TLS to avoid
                                                  // needing certificates.
                                                  .usePlaintext()
                                                  .build();
    try {
      VexillumClient client = new VexillumClient(channel);
      client.getFlag(flagName);
      client.setFlag(flagName, true);
      client.getFlag(flagName);
      client.setFlag(flagName, false);
      client.getFlag(flagName);
    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
      // resources the channel should be shut down when it will no longer be used. If it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
