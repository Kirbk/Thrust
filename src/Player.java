import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utilities.Vector2f;

import java.util.ArrayList;

public class Player extends Entity {

    private final double ROTATION_SPEED = 30.0;
    private final double THRUST_DELTAV = 16.5;
    private final int maxShootCooldown = 5;
    private int shootCooldown = 0;


    public Player(Image icon, Vector2f position, Vector2f size) {
        super(icon, position, size);
        Main.getWorld().setGravity(0.0f);
        setDrawAngle(-45);
    }

//    @Override
//    public void draw(GraphicsContext gc, Vector2f camPosition) {
//        gc.save();
//        rotate(gc, drawAngle + angle, size.x / 2, size.y / 2);
//        gc.drawImage(icon, 0, 0, size.x, size.y);
//        gc.restore();
//    }

    public void update(double deltaT, ArrayList<String> input) {
        super.update(deltaT);

        setVelX(getVelocity().x * 0.99f);

        if (input.contains("UP") || input.contains("W")) {
            changeVelX((float)THRUST_DELTAV * (float)Math.sin(Math.toRadians(getAngle())));
            changeVelY(-(float)THRUST_DELTAV * (float)Math.cos(Math.toRadians(getAngle())));

            if (shootCooldown <= 0) {
                Main.getWorld().addEntity(new Bullet(new Vector2f(22 + getPosition().x + (-(getSize().x) / 2 * (float)Math.sin(Math.toRadians(getAngle()))),
                        20 + getPosition().y + ((getSize().y + 10) / 2 * (float)Math.cos(Math.toRadians(getAngle())))),
                        new Vector2f(5, 10),
                        getVelocity(),
                        getAngle() + 180,
                        200,
                         this));

                shootCooldown = maxShootCooldown;
            }
        }

        if (input.contains("RIGHT") || input.contains("D")) {
            changeRotationVelocity(ROTATION_SPEED);
        }

        if (input.contains("LEFT") || input.contains("A")) {
            changeRotationVelocity(-ROTATION_SPEED);
        }

        if (input.contains("R")) {
            Main.getWorld().clearEntities();
            Main.getWorld().addEntity(this);
        }

        if (shootCooldown > 0) shootCooldown--;
    }
}
