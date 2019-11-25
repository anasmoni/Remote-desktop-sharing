import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

class ClientHandler extends Thread {

    private JDesktopPane jDesktopPane = null;
    private Socket socket = null;
    private JInternalFrame jInternalFrame = new JInternalFrame("Client Screen", true, true, true);
    private JPanel jPanel = new JPanel();
    InputStream inputStream=null;
    public ClientHandler(Socket sct, JDesktopPane jDesktopPane) {
        socket = sct;
        this.jDesktopPane = jDesktopPane;
        try {
            inputStream = socket.getInputStream();
        }catch (Exception e){
        }
        start();
    }

    public void run(){

        Rectangle rectangle = null;
        ObjectInputStream objectInputStream = null;
        show_client_screen();

        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            rectangle =(Rectangle) objectInputStream.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        new ClientScreenReciever(objectInputStream,jPanel);
       // new recv_bytes(objectInputStream,jPanel);// changing
        new send_command(socket,jPanel,rectangle);
    }

    public void show_client_screen(){
        jInternalFrame.setLayout(new BorderLayout());
        jInternalFrame.getContentPane().add(jPanel,BorderLayout.CENTER);
        jInternalFrame.setSize(100,100);
        jDesktopPane.add(jInternalFrame);
        try {
            jInternalFrame.setMaximum(true);
        }catch (Exception e){
        }

        jPanel.setFocusable(true);
        jInternalFrame.setVisible(true);
    }

}
