import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class threeD3 extends Application {
    final Image CARD_IMAGE = new Image("all.jpg");
    final int W = (int) (CARD_IMAGE.getWidth()/3);
    final int H = (int) CARD_IMAGE.getHeight()/3;
    String xyz=null;
    @Override
    public void start(Stage stage) throws Exception {
        Node card = createCard();

        stage.setScene(createScene(card));
        stage.show();

        RotateTransition rotator = createRotator(card,"X");
        rotator.play();
        tune();
    }

    private Scene createScene(Node card) {
        StackPane root = new StackPane();
        root.getChildren().addAll(card, new AmbientLight(Color.DARKCYAN));

        Scene scene = new Scene(root, W + 1200, H + 500, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.MIDNIGHTBLUE.darker().darker().darker().darker());
        scene.setCamera(new PerspectiveCamera());

        return scene;
    }

    public RotateTransition createRotator(Node card,String xyz) {
        RotateTransition rotator = new RotateTransition(Duration.millis(10000), card);
        if(xyz.equals("X"))rotator.setAxis(Rotate.X_AXIS);
        else if(xyz.equals("Y"))rotator.setAxis(Rotate.Y_AXIS);
        else rotator.setAxis(Rotate.Z_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
    }

    public Node createCard() {
        MeshView card = new MeshView(createCardMesh());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(CARD_IMAGE);
        card.setMaterial(material);

        return card;
    }

    private TriangleMesh createCardMesh() {
        TriangleMesh mesh = new TriangleMesh();

        mesh.getPoints().addAll(-3 * W, -3 * H , 0, 3 * W, -3 * H, 0, -1 * W*3, H, 0, 3*W,  3*H, 0);
        mesh.getFaces().addAll(0,0,2, 2, 3, 3, 3, 3,1,1,0,0);
        mesh.getFaces().addAll(0, 4, 3, 7, 2, 6, 3,7, 0, 4, 1, 5);
        mesh.getTexCoords().addAll(0, 0,2.0f, 0, 0, 1,2.0f, 1,2.0f, 0, 1, 0, 2.0f, 1, 1, 1);
        return mesh;
    }

    public void tune(){
        final URL resource = getClass().getResource("music.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}