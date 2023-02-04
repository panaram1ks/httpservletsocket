package com.parom.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final ExecutorService pool;
    private final int port;
    private boolean stopped;

    public HttpServer(int port, int poolSize) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            while (!stopped){
                Socket socket = server.accept();
                System.out.println("Socket accepted");
                pool.submit(() -> processSocket(socket));
            }
//            processSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void processSocket(Socket socket) {
        try (socket;
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            //step 1 handle request
            System.out.println("Request: " + new String(dataInputStream.readNBytes(400)));
            Thread.sleep(10_000l);
            //step 2 handle response
            byte[] resourcesBody = Files.readAllBytes(Path.of("resources", "example.html"));
            var headers = """
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %s
                    """.formatted(resourcesBody.length)
                    .getBytes();
            dataOutputStream.write(headers);
            dataOutputStream.write(System.lineSeparator().getBytes());
            dataOutputStream.write(resourcesBody);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
