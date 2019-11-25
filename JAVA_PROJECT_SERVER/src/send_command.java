import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class send_command extends KeyAdapter implements KeyListener, MouseMotionListener,MouseListener,MouseWheelListener {

    private Socket cSocket = null;
    private JPanel cPanel = null;
    private PrintWriter writer = null;
    private Rectangle clientScreenDim = null;

    send_command(Socket s, JPanel p, Rectangle r) {
        cSocket = s;
        cPanel = p;
        clientScreenDim = r;
        cPanel.addKeyListener(this);
        cPanel.addMouseListener(this);
        cPanel.addMouseMotionListener(this);
        cPanel.addMouseWheelListener(this);
        try {
            writer = new PrintWriter(cSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public void mouseDragged(MouseEvent e) {
        writer.println(EnumCommands.DRAG_MOUSE.getAbbrev());
        writer.flush();
    }

    public void mouseMoved(MouseEvent e) {
        double xScale = clientScreenDim.getWidth()/cPanel.getWidth();
        //System.out.println("xScale: " + xScale);
        double yScale = clientScreenDim.getHeight()/cPanel.getHeight();
        //System.out.println("yScale: " + yScale);
       // System.out.println("Mouse Moved");
        writer.println(EnumCommands.MOVE_MOUSE.getAbbrev());
        writer.println((int)(e.getX() * xScale));
        writer.println((int)(e.getY() * yScale));
        writer.flush();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse Pressed");
        writer.println(EnumCommands.PRESS_MOUSE.getAbbrev());
        int button = e.getButton();
        int x = 16;
        if (button == 3) x = 4;
        writer.println(x);
        writer.flush();
    }

    public void mouseReleased(MouseEvent e) {
        //System.out.println("Mouse Released");
        writer.println(EnumCommands.RELEASE_MOUSE.getAbbrev());
        int button = e.getButton();
        int x = 16;
        if (button == 3)x = 4;
        writer.println(x);
        writer.flush();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        //System.out.println("Key Pressed");
        writer.println(EnumCommands.PRESS_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("Mouse Released");
        writer.println(EnumCommands.RELEASE_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        writer.println(EnumCommands.SCROLL_MOUSE.getAbbrev());
       // System.out.println("Server wheel");
        int up_down = e.getWheelRotation();
        writer.println(e.getScrollAmount()*up_down);
    }

}
