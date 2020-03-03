package bg.sofia.uni.fmi.mjt.server;

import bg.sofia.uni.fmi.mjt.server.utils.Commands;
import bg.sofia.uni.fmi.mjt.server.utils.MessageConstants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatServer {
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;
    private static final String DELIMITER = " ";
    private static final int POSITION_OF_COMMAND = 0;
    private static final int POSITION_OF_NICKNAME = 1;
    private static final int POSITION_OF_MESSAGE = 1;
    private static final int POSITION_OF_ARGUMENTS = 2;

    private ServerSocketChannel serverSocketChannel;
    private Map<String, SelectionKey> activeUsers;
    private ByteBuffer buffer;
    private boolean isServerStarted;

    public static void main(String[] args) {
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            ChatServer server = new ChatServer(socketChannel);
            server.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ChatServer(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
        activeUsers = new HashMap<>();
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        isServerStarted = true;
    }

    public void start() {
        try {
            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (isServerStarted) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }

                handleReadyChannels(selector);
            }
        } catch (IOException e) {
            System.err.println("There is a problem with the server socket: " + e.getMessage());
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                System.err.println("Exception thrown by close Socket: " + e.getMessage());
            }
        }
    }

    private void handleReadyChannels(Selector selector) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                SocketChannel client = (SocketChannel) key.channel();
                if (client == null) {
                    continue;
                }
                String[] commandArguments = getCommandArguments(client);
                executeCommand(commandArguments, key);
            } else if (key.isAcceptable()) {
                accept(selector, key);
            }

            keyIterator.remove();
        }
    }

    private String[] getCommandArguments(SocketChannel client) throws IOException {
        buffer.clear();
        client.read(buffer);
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        String commandArguments = new String(bytes);

        return commandArguments.split(DELIMITER);
    }

    private void executeCommand(String[] arguments, SelectionKey key) throws IOException {
        String command = arguments[POSITION_OF_COMMAND].trim();
        String messageToReceive;
        switch (command) {
            case Commands.NICK_COMMAND:
                String nickName = arguments[POSITION_OF_NICKNAME].trim();
                registerUser(nickName, key);
                break;
            case Commands.SEND_COMMAND:
                SocketChannel receiver = (SocketChannel) activeUsers.get(arguments[1].trim()).channel();
                messageToReceive = getPrefixMessage() + key.attachment().toString()
                        + ": " + arguments[POSITION_OF_ARGUMENTS].trim();
                sendMessage(messageToReceive, receiver);
                break;
            case Commands.SEND_ALL_COMMAND:
                messageToReceive = getPrefixMessage() + key.attachment().toString()
                        + ": " + arguments[POSITION_OF_MESSAGE].trim();
                sendMessageToAllActiveUsers(messageToReceive, key);
                break;
            case Commands.LIST_USERS_COMMAND:
                messageToReceive = activeUsers.keySet().toString();
                sendMessage(messageToReceive, (SocketChannel) key.channel());
                break;
            case Commands.DISCONNECT_COMMAND:
                activeUsers.remove(key.attachment().toString());
                sendMessage(MessageConstants.DISCONNECT_FROM_SERVER_MESSAGE, (SocketChannel) key.channel());
                break;
            default:
                sendMessage(MessageConstants.INVALID_COMMAND_MESSAGE, (SocketChannel) key.channel());
                break;
        }
    }

    private void registerUser(String nickName, SelectionKey key) throws IOException {
        key.attach(nickName);
        SocketChannel client = (SocketChannel) key.channel();
        if (activeUsers.get(nickName) != null) {
            sendMessage(MessageConstants.CLIENT_IS_ALREADY_REGISTERED, client);
        } else if (activeUsers.containsKey(nickName)) {
            sendMessage(MessageConstants.USERNAME_ALREADY_EXISTS_MESSAGE, client);
        } else {
            activeUsers.put(nickName, key);
        }
    }

    private String getPrefixMessage() {
        return "[" + LocalDateTime.now() + "] ";
    }

    private void sendMessageToAllActiveUsers(String message, SelectionKey key) throws IOException {
        for (Map.Entry<String, SelectionKey> currentUser : activeUsers.entrySet()) {
            if (!currentUser.getValue().equals(key)) {
                sendMessage(message, (SocketChannel) currentUser.getValue().channel());
            }
        }
    }

    private void sendMessage(String message, SocketChannel client) throws IOException {
        message += System.lineSeparator();
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();
        client.write(buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }
}