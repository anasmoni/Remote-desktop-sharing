import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client {

    Socket socket = null;

    public static void main(String[] args){
        String ip = JOptionPane.showInputDialog("Please enter server IP");
        String port = JOptionPane.showInputDialog("Please enter server port");
        new Client().initialize(ip, Integer.parseInt(port));
    }

    public void initialize(String ip, int port ){

        Robot robot = null;
        Rectangle rectangle = null;

        try {
            System.out.println("Connecting to server ..........");
            socket = new Socket(ip, port);
            System.out.println("Connection Established.");

            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            robot = new Robot(gDev);


            drawGUI();

            new send_screenshot(socket,robot,rectangle);
            //new snd_bytes(socket,robot,rectangle);
            new Server_command(socket,robot);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Client");
        JButton button= new JButton("Exit");

        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

                                      public void actionPerformed(ActionEvent e) {
                                          System.exit(0);
                                      }
                                  }
        );
        frame.setVisible(true);
    }
}
