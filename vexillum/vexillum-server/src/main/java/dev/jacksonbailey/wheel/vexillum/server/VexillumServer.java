package dev.jacksonbailey.wheel.vexillum.server;

import dev.jacksonbailey.wheel.vexillum.api.Flag;
import dev.jacksonbailey.wheel.vexillum.api.FlagServiceGrpc;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.GetFlagStateRequest;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateReply;
import dev.jacksonbailey.wheel.vexillum.api.SetFlagStateRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server that manages startup/shutdown of a {@code FlagService} server.
 */
public class VexillumServer {

  private static final Logger log = LoggerFactory.getLogger(VexillumServer.class);

  private Server server;

  private void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
                          .addService(new FlagServiceImpl())
                          .build()
                          .start();
    log.info("Server started, listening on {}", port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          VexillumServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final VexillumServer server = new VexillumServer();
    server.start();
    server.blockUntilShutdown();
  }

  static class FlagServiceImpl extends FlagServiceGrpc.FlagServiceImplBase {

    private final Map<String, Boolean> dataStore = new HashMap<>();

    @Override
    public void getFlagState(GetFlagStateRequest req,
        StreamObserver<GetFlagStateReply> responseObserver) {

      var flagName = req.getName();
      var replyBuilder = GetFlagStateReply.newBuilder();

      if (dataStore.containsKey(flagName)) {
        replyBuilder.setCurrentFlag(
            Flag.newBuilder()
                .setName(flagName)
                .setState(dataStore.get(flagName))
                .build()
        );
      }

      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }

    @Override
    public void setFlagState(SetFlagStateRequest req,
        StreamObserver<SetFlagStateReply> responseObserver) {

      var flagName = req.getNewFlag().getName();
      var replyBuilder = SetFlagStateReply.newBuilder();

      if (dataStore.containsKey(flagName)) {
        replyBuilder.setPreviousFlag(
            Flag.newBuilder()
                .setName(flagName)
                .setState(dataStore.get(flagName))
                .build()
        );
      }

      dataStore.put(flagName, req.getNewFlag().getState());

      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

}
