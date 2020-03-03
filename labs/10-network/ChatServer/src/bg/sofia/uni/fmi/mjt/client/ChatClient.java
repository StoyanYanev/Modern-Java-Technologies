package bg.sofia.uni.fmi.mjt.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "localhost";
    private static final String ENCODING = "UTF-8";
    private static final String DISCONNECT_COMMAND = "disconnect";
    private static final String NICK_COMMAND = "nick ";
    private static final String SEND_ALL_COMMAND = "send-all ";

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }

    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, ENCODING), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            Thread client = new Thread(new ServerResponseHandler(socketChannel));
            client.start();

            System.out.print(">");
            while (true) {
                String message = scanner.nextLine();
                if (message.contains(NICK_COMMAND)
                        || message.contains(SEND_ALL_COMMAND)) {
                    System.out.print(">");
                }
                writer.println(message);
                if (message.equals(DISCONNECT_COMMAND)) {
                    client.join();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}