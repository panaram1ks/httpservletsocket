package com.parom.http.socker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerRunner {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(7777);
             Socket socket = serverSocket.accept();
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());) {

            var scanner = new Scanner(System.in);

            String key = "";
            while(!key.equals("stop") && scanner.hasNextLine()){
                key = dataInputStream.readUTF();
                System.out.println("Client say: " + key);
                var response = scanner.nextLine();
                dataOutputStream.writeUTF(response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
