package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ana on 02.07.2017.
 */
public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
        private Socket socket;

        public Handler (Socket socket){
            this.socket = socket;
        }

        public void run(){
            String userName = "";
            ConsoleHelper.writeMessage("Установлено сообщение с адресом " + socket.getRemoteSocketAddress());
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection,userName);
                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Возникла ошибка при работе с удаленным адресом");
            }finally {
                if (userName != null)
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                ConsoleHelper.writeMessage("Соединение закрыто");
            }
        }


        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                String clientName = message.getData();
                if (message.getType() == MessageType.USER_NAME &&
                        !clientName.isEmpty() &&
                        !connectionMap.containsKey(clientName) &&
                        connectionMap.get(clientName) == null){
                    connectionMap.put(clientName, connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return clientName;
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> map : connectionMap.entrySet()) { // отправляет через connection информацию
                // всем клиентам, что пользователь такой то присоединился к чату
                if (userName != map.getKey()) {
                    connection.send(new Message(MessageType.USER_ADDED, map.getKey()));
                }
            }
        }
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                }
                else
                    ConsoleHelper.writeMessage("Ошибка отправки сообщения");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            ConsoleHelper.writeMessage("Сервер запущен");

            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        }catch (Exception ex){
            ConsoleHelper.writeMessage("Ошибка. Сервер закрыт");
        }
    }

    public static void sendBroadcastMessage(Message message){
        for (Map.Entry<String, Connection> d: connectionMap.entrySet()){
            try {
                d.getValue().send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Сообщение не было отправлено: " + d.getKey());
            }
        }
    }
}
