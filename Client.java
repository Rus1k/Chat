package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ruslan on 14.10.2015.
 */
public class Client  {

    public static void main(String[] args) throws IOException {
        System.out.println("Client START");
        Socket socket =  new Socket("127.0.0.1", 4444);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
        String fuser, fserver;
        while ((fuser = inu.readLine()) != null){
            out.println(fuser);
            fserver = in.readLine();
            System.out.println(fserver);
            if(fuser.equalsIgnoreCase("close")) break;
            if(fuser.equalsIgnoreCase("exit")) break;
        }
        out.close();
        in.close();
        inu.close();
        socket.close();
    }
}
