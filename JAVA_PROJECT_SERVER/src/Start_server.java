import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.URL;

public class Start_server extends Application {
    final Image CARD_IMAGE = new Image("gray.jpg");
   // final Image img2 = new Image("gray.jpg");
    final int W = (int) (CARD_IMAGE.getWidth());
    final int H = (int) CARD_IMAGE.getHeight();

    @Override
    public void start(Stage stage) throws Exception {
        Node card = createCard();

        stage.setScene(createScene(card));
        stage.show();

        RotateTransition rotator = createRotator(card);
        rotator.play();
        tune();
    }

    private Scene createScene(Node card) {
        /*Button btn = new Button();
        btn.setText("Stop!");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });*/

        StackPane root = new StackPane();
        root.getChildren().addAll(card, new AmbientLight(Color.WHITE));
        Scene scene = new Scene(root, W + 1200, H + 500, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.MIDNIGHTBLUE.darker().darker().darker().darker());
        scene.setCamera(new PerspectiveCamera());

        return scene;
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(10000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
    }

    private Node createCard() {
        MeshView card = new MeshView(createCardMesh());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(CARD_IMAGE);
        card.setMaterial(material);

        return card;
    }

    private TriangleMesh createCardMesh() {
        TriangleMesh mesh = new TriangleMesh();

        mesh.getPoints().addAll(-1 * W, -1 * H , 0, 1 * W, -1 * H, 0, -1 * W, 1 * H, 0, 1 * W, 1 * H, 0);
        mesh.getFaces().addAll(0,0,2, 2, 3, 3, 3, 3,1,1,0,0);
        mesh.getFaces().addAll(0, 4, 3, 7, 2, 6, 3, 7, 0, 4, 1, 5);
        //mesh.getFaces().addAll(0, 3, 3, 7, 2, 5, 3, 6, 0, 3, 1, 4);
        mesh.getTexCoords().addAll(0, 0,2.0f, 0, 0, 1,2.0f, 1,2.0f, 0, 1, 0, 2.0f, 1, 1, 1);

        return mesh;
    }

    public void tune(){
        final URL resource = getClass().getResource("music.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void main(String[] args)throws Exception {
        launch();
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        String port = JOptionPane.showInputDialog("Enter  port");
        int p = Integer.parseInt(port);
        new Server().connect_client(p);
    }
}