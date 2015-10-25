package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    public static ServerSocket serverSocket = null;
    public BufferedReader inServer = null;
    public PrintWriter outFromServer;
    public Socket socketFromClient = null;
    static List<Socket> clientList = new CopyOnWriteArrayList<>();
    static Map<String, Socket> stringSocketMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(4444);
        System.out.println("Server start!");
        Server server = new Server();
        server.run();

    }

    @Override
    public void run() {
        while (true) {
            try {
                socketFromClient = serverSocket.accept();
                System.out.println("new connect");
                inServer = new BufferedReader(new InputStreamReader(socketFromClient.getInputStream()));
                clientList.add(socketFromClient);
                Server server = new Server();
                server.start();
                break;
            } catch (IOException e) {
                System.out.println("cant accept");
            }
        }

        System.out.println("wait massage");
        try {
            String textFromClient;
            while (true) {
                textFromClient = inServer.readLine();
                String [] arrayTextFromClient = textFromClient.split(":");
                String login = arrayTextFromClient[0];
                stringSocketMap.put(login, socketFromClient);
                if (textFromClient != null) {
                    System.out.println(textFromClient);
                    Iterator<Map.Entry<String, Socket>> iterator = stringSocketMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Socket> socket = iterator.next();
                        if(!(login.equals(socket.getKey()))){
                        outFromServer = new PrintWriter(socket.getValue().getOutputStream(), true);
                        outFromServer.println(textFromClient);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outFromServer.close();
            inServer.close();
            socketFromClient.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}