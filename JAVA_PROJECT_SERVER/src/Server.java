import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private JFrame jFrame = new JFrame();

    private JDesktopPane jDesktopPane = new JDesktopPane();

    public void show_gui(){
        jFrame.add(jDesktopPane,BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setExtendedState(jFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        jFrame.setVisible(true);
    }
    public static void main(String args[])throws Exception{
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        /*String port = JOptionPane.showInputDialog("Enter  port");
        int p = Integer.parseInt(port);
        new Server().connect_client(p);*/
    }

    public void connect_client(int port){

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            show_gui();
            while(true){
                Socket socket = serverSocket.accept();
               //System.out.println("Yep Yup!! , New client Connected successfully........");
                new ClientHandler(socket,jDesktopPane);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
