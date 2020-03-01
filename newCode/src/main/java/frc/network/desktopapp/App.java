
package frc.network.desktopapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import frc.network.desktopapp.gameutil.text.Argument;
import frc.network.desktopapp.gameutil.text.Console;

public class App {
    static ServerSocket s;
    static Socket test;

    public static void main(String[] unicorns) {
        Console.s.setTheme(Console.theme.shell2);
        Argument cmd = Argument.getArgs("");
        int state = 0;
        Console.s.println("type ? for help");
        int port=3655;
        s=null;
        Socket client=null;
        while (true){
            try {
                s = new ServerSocket(port);
                Console.s.println("Server socket initialized successfully.");
                Console.s.println("PORT:"+port+"");
                Console.s.println("IP: "+s.getInetAddress().getCanonicalHostName());
                break;//break out of loop
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Console.s.println("ERROR: failed to create server socket on port "+port+".");
                Console.s.println("Would you like to try a different port? (Y/N)");
                while (true){
                    String attempt=Console.s.readLine();
                    if (attempt.equalsIgnoreCase("Y")){
                        port=Console.s.readInt();
                        break;
                    } else if (attempt.equalsIgnoreCase("N")) {
                        Console.s.println("Closing program.");
                        Console.s.pause();
                        System.exit(1);
                    } else {
                        Console.s.println("Invalid input: please inter Y or N (Y for yes, N for no)");
                    }
                }
            }
        }
        while (!cmd.cmd().equalsIgnoreCase("exit")){
            switch (state){
                case 0:
                    //command mode
                    Console.s.print(">>");
                    cmd=Argument.getArgs(Console.s.readLine());
                    switch (cmd.cmd()){
                        case "?":
                            //print help
                            Console.s.println("? - help");
                            Console.s.println("exit - exit");
                            Console.s.println("accept - attempt connection");
                            Console.s.println("test <ip> - attempt connect to self");
                        break;
                        case "accept":
                            Console.s.println("Waiting for connection.");

                            try {
                                client = s.accept();
                                Console.s.println("Socket accepted.");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        break;
                        case "test":
                            test=null;
                            Thread thread=new Thread(){
                                @Override
                                public void run(){
                                    try {
                                        test=s.accept();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            };
                            thread.start();
                            try {
                                client=new Socket("10.0.0.11", port);
                                Console.s.println("Client: Connection successful");
                                Console.s.println("Connected to "+test.getInetAddress().getHostAddress());
                            } catch (UnknownHostException e){

                            } catch (IOException e){

                            }
                        break;
                        default:
                            Console.s.println("Invalid command; type ? for help");
                        break;
                        
                    }
                break;
            }
        }
        System.exit(0);
    }
}
