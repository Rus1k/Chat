package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    public static ServerSocket serverSocket = null;
    public BufferedReader in = null;
    public BufferedReader in1 = null;
    public PrintWriter out = null;
    public Socket fromClient = null;
    static List<Socket> clientList = new CopyOnWriteArrayList<>();

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
                fromClient = serverSocket.accept();
                System.out.println("new connect");
                in = new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
                out = new PrintWriter(fromClient.getOutputStream(), true);
                clientList.add(fromClient);
                Server server = new Server();
                server.start();
                break;
            } catch (IOException e) {
                System.out.println("cant accept");
            }
        }

        System.out.println("wait massage");
        try {
            String input;
            while (true) {
                input = in.readLine();
                if (input != null) {
                    System.out.println(input);
                    for (Socket socket : clientList) {
                        PrintWriter outer = new PrintWriter(socket.getOutputStream(),true);
                        outer.println(input);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
            in.close();
            fromClient.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
