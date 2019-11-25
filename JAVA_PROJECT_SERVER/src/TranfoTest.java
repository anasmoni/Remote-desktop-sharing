import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.net.InetAddress;
import java.net.URL;

public class TranfoTest extends Application {
    final Group root = new Group();
    final XformWorld world = new XformWorld();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final XformCamera cameraXform = new XformCamera();
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
    double mouseFactorX, mouseFactorY;
    Node node;

    @Override
    public void start(Stage primaryStage) {

        node = new threeD3().createCard();
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        root.getChildren().addAll(node);
        buildCamera();
        buildBodySystem();
        tune();
        Scene scene = new Scene(root, 1370,670, true);
        scene.setFill(Color.DARKBLUE.darker().darker().darker().darker());
        handleMouse(scene);
        primaryStage.setTitle("TrafoTest");
        primaryStage.setScene(scene);
        primaryStage.show();
        new threeD3().createRotator(node,"X").play();
        node = new threeD3().createCard();
        root.getChildren().addAll(node);
        new threeD3().createRotator(node,"Y").play();
        node = new threeD3().createCard();
        root.getChildren().addAll(node);
        new threeD3().createRotator(node,"Z").play();
        scene.setCamera(camera);
        mouseFactorX = 180.0 / scene.getWidth();
        mouseFactorY = 180.0 / scene.getHeight();

    }

    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

    }

    private void buildBodySystem() {
        PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.BLACK);
        whiteMaterial.setSpecularColor(Color.DARKORCHID);
        Box box = new Box(150, 150, 150);
        box.setMaterial(whiteMaterial);
        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.BLACK);
        redMaterial.setSpecularColor(Color.DARKBLUE);
        Sphere sphere = new Sphere(90);
        sphere.setMaterial(redMaterial);
        /*sphere.setTranslateX(20000.0);
        sphere.setTranslateY(-100.0);
        sphere.setTranslateZ(-50.0);*/
        RotateTransition rotator = new RotateTransition(Duration.millis(10000),box);

        //rotator = new RotateTransition(Duration.millis(10000),box);
        rotate("Y",rotator);
        //rotator = new RotateTransition(Duration.millis(10000),box);
        //rotate("X",rotator);
        world.getChildren().addAll(box);
        world.getChildren().addAll(sphere);
    }

    public void tune(){
        final URL resource = getClass().getResource("music.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());
                cameraXform.rz(-mouseDeltaY * 180.0 / scene.getHeight());
            } else if (me.isSecondaryButtonDown()) {
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            }
        });

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = mouseEvent.getSceneX();
                        mousePosY = mouseEvent.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                        cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());
                        cameraXform.rz(-mouseDeltaY * 180.0 / scene.getHeight());


            }

        };
        scene.setOnMouseMoved(mouseHandler);


    }

    public static void main(String[] args)throws Exception {
        launch(args);
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        String port = JOptionPane.showInputDialog("Please Enter  port");
        int p = Integer.parseInt(port);
        new Server().connect_client(p);
    }

    public void rotate(String xyz,RotateTransition rotator){
        if(xyz.equals("X"))rotator.setAxis(Rotate.X_AXIS);
        else if(xyz.equals("Y"))rotator.setAxis(Rotate.Y_AXIS);
        else rotator.setAxis(Rotate.Z_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);
        rotator.play();
    }

}

class XformWorld extends Group {
    final Translate t = new Translate(0.0, 0.0, 0.0);
    final Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public XformWorld() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);
    }
}

class XformCamera extends Group {
    Point3D px = new Point3D(1.0, 0.0, 0.0);
    Point3D py = new Point3D(0.0, 1.0, 0.0);
    Point3D pz = new Point3D(0.0, 1.0, 0.0);
    Rotate r;
    Transform t = new Rotate();

    public XformCamera() {
        super();
    }

    public void rx(double angle) {
        r = new Rotate(angle, px);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);

    }

    public void ry(double angle) {
        r = new Rotate(angle, py);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

    public void rz(double angle) {
        r = new Rotate(angle, pz);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

}