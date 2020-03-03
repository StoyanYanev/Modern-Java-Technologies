package bg.sofia.uni.fmi.mjt.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

public class ServerResponseHandler implements Runnable {
    private static final String ENCODING = "UTF-8";
    private static final String DISCONNECT_MESSAGE = "Disconnected from server";
    private SocketChannel socketChannel;

    public ServerResponseHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, ENCODING))) {
            while (true) {
                String reply = reader.readLine();
                if (reply == null) {
                    break;
                }
                System.out.println(reply);
                if (!reply.equals(DISCONNECT_MESSAGE)) {
                    System.out.print(">");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}