package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    public static ServerSocket serverSocket = null;
    public BufferedReader in = null;
    public PrintWriter out = null;
    public Socket fromClient = null;
    List<PrintWriter> clientList = new CopyOnWriteArrayList<PrintWriter>();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(4444);
        System.out.println("Server start!");
        Server server = new Server();
        server.run();

    }

    @Override
    public void run() {
        System.out.println("new connect");
        while (true) {
            try {
                fromClient = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
                out = new PrintWriter(fromClient.getOutputStream(), true);
                clientList.add(out);
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
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("exit")) break;
                for (PrintWriter printWriter : clientList) {
                    printWriter.println(input);
                }
                System.out.println(input);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  out.close();
        try {
            in.close();
            fromClient.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
