package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    static Socket socket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static String login;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client START");
        System.out.println("Please, enter your nickname !Then you can join the dialog ");
        login = scanner.nextLine();
        socket = new Socket("127.0.0.1", 4444);
        Client client = new Client();
        client.start();
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in));
        out.println(login + ":" + "");
        out.flush();
        String fromUser;
        while (true) {
            if ((fromUser = inFromConsole.readLine()) != null) {
                out.println(login + ": " + fromUser);
            }
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromServer;
            while (true) {
                if ((fromServer = in.readLine()) != null) {
                    System.out.println(fromServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}