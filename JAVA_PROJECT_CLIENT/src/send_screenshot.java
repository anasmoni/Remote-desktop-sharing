import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

class send_screenshot extends Thread {

    Socket socket = null;
    Robot robot = null;
    Rectangle rectangle = null;

    public send_screenshot(Socket socket, Robot robot,Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run(){
        ObjectOutputStream oos = null;


        try{

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(rectangle);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        while(true){

            BufferedImage image = robot.createScreenCapture(rectangle);

            ImageIcon imageIcon = new ImageIcon(image);

            try {
                System.out.println("before sending image");
                oos.writeObject(imageIcon);
                oos.reset();
                System.out.println("New screenshot sent");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            /*try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }*/
        }

    }

}
