package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {
    static Socket socket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;

    public static void main(String[] args) throws IOException {
        socket = new Socket("127.0.0.1", 4444);
        System.out.println("Client START");
        Client client = new Client();
        client.start();
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inForConsole = new BufferedReader(new InputStreamReader(System.in));
        String fuser;
        while (true) {
            if((fuser = inForConsole.readLine()) != null){
                out.println(fuser);
            }
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String forServer;
            while (true) {
                if((forServer = in.readLine()) != null) {
                    System.out.println(forServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}