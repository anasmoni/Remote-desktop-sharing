import javafx.scene.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ObjectInputStream;

class ClientScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    InputStream inputStream=null;

    public ClientScreenReciever(ObjectInputStream ois, JPanel p) {
        cObjectInputStream = ois;
        inputStream = ois;
        cPanel = p;
        start();
    }

    public void run(){
        
            try {

                while(true){
                    ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                    System.out.println("New screenshot recieved");
                    Image image = imageIcon.getImage();
                    image = image.getScaledInstance(cPanel.getWidth(),cPanel.getHeight(),Image.SCALE_FAST);
                    Graphics graphics = cPanel.getGraphics();
                    graphics.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                    System.out.println("Drawn.................");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
     }
}
