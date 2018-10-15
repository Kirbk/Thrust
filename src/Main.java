import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utilities.Vector2f;

import java.util.ArrayList;

public class Main extends Application {
    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 600;
    private static World world;

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static void setScreenHeight(int height) {
        SCREEN_HEIGHT = height;
    }

    public static void setScreenWidth(int width) {
        SCREEN_WIDTH = width;
    }

    public static World getWorld() {
        return world;
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Thrust");
        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<>();

        primaryStage.getScene().setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    if ( !input.contains(code) ) {
                        if (code.equals("ESCAPE")) {
                            getWorld().setPaused(!getWorld().getPaused());
                        }

                        input.add(code);
                    }
                });

        primaryStage.getScene().setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove( code );
                });

        primaryStage.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            setScreenWidth(newSceneWidth.intValue());
            canvas.setWidth(getScreenWidth());
        });

        primaryStage.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            setScreenHeight(newSceneHeight.intValue());
            canvas.setHeight(getScreenHeight());
        });


        GraphicsContext gc = canvas.getGraphicsContext2D();

        world = new World(true);
        Player player = new Player(new Image("rocket.png"), new Vector2f(0, 0), new Vector2f(50, 50));

        world.addEntity(player);

        Entity entity = new Entity(new Image("rocket2-512.png"), new Vector2f(0, 0), new Vector2f(50, 50));
        //world.addEntity(entity);

        //world.addEntity(new Bullet(new Vector2f(100, 100), new Vector2f(5, 10), 0));

        AnimationTimer loop = new AnimationTimer()
        {
            float camX = player.getPosition().x + SCREEN_WIDTH / 2.0f;
            float camY = player.getPosition().y + SCREEN_HEIGHT / 2.0f;

            double lastNanoTime = 0;
            public void handle(long currentNanoTime)
            {
                if (lastNanoTime == 0) lastNanoTime = currentNanoTime;
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

                // *** UPDATE BLOCK *** //

                world.updateEntities(elapsedTime);

                // Test Entity -- Delete
                entity.setY(0);
                entity.setRotationVelocity(10);

                if (!getWorld().getPaused())
                    player.update(elapsedTime, input);

                // *** END UPDATE BLOCK *** //

                camX = player.getPosition().x - SCREEN_WIDTH / 2.0f;
                camY = player.getPosition().y - SCREEN_HEIGHT / 2.0f;

                world.drawEntities(gc, new Vector2f(camX, camY));

                gc.setFill( Color.BLACK );
                Font theFont = Font.font( "Times New Roman", FontWeight.NORMAL, 12 );
                gc.setFont( theFont );
                gc.fillText("Vel: ( " + String.valueOf((int)player.getVelocity().x)  + ", " + String.valueOf((int)player.getVelocity().y) + ")", 10, 20);
                gc.fillText("Pos: (" + String.valueOf((int)player.getPosition().x) + ", " + String.valueOf((int)player.getPosition().y) + ")", 10, 32);
            }
        };

        loop.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
