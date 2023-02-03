package com.parom.http.socker;


import java.io.IOException;
import java.net.*;

public class DatagramRunner {
    public static void main(String[] args) throws IOException {



        var inetAddress= InetAddress.getByName("localhost");
        try (var datagramSocket = new DatagramSocket()) {

//            ----------> [buffer] <------------
//            byte[] bytes = new byte[512];
            byte[] bytes = "Hello from UDP client".getBytes();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, 7777);
            datagramSocket.send(packet);

        }
    }
}
