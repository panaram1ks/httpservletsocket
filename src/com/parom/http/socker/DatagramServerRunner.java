package com.parom.http.socker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramServerRunner {

    public static void main(String[] args) throws IOException {
        try (var datagramSocket = new DatagramSocket(7777)) {
            byte[] buffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);

            System.out.println(packet.getData().toString());
        }
    }
}
