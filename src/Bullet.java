import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utilities.Vector2f;

public class Bullet extends Entity {
    private final double RANDOM_FACTOR = 50.0;
    private float BULLET_SPEED = 250.0f;

    private int lifeTime;
    private int life = 0;

    private Entity parent;

    public Bullet(Vector2f position, Vector2f size, Vector2f startingVelocity, double angle, int lifeTime, Entity parent) {
        super(null, position, size, angle);

        this.lifeTime = lifeTime;
        this.parent = parent;

        angle += Math.floor(Math.random() * RANDOM_FACTOR / 2);
        angle -= Math.floor(Math.random() * RANDOM_FACTOR / 2);

        setAngle(angle);

        changeVelX(BULLET_SPEED * (float)Math.sin(Math.toRadians(getAngle())) + startingVelocity.x);
        changeVelY(-BULLET_SPEED * (float)Math.cos(Math.toRadians(getAngle())) + startingVelocity.y);
    }

    void update(double deltaT) {
        super.update(deltaT);

         if (this.life >= this.lifeTime && lifeTime != -1) {
             setRemove(true);
         }

         life++;
    }

    // Draw from center
    @Override
    void draw(GraphicsContext gc, Vector2f camPosition) {
        gc.save();
        rotate(gc, getDrawAngle() + getAngle(), getPosition().x - camPosition.x + getSize().x / 2, getPosition().y - camPosition.y + getSize().y / 2);
        gc.setFill(Color.BLACK);
        gc.fillRect(getPosition().x - camPosition.x, getPosition().y - camPosition.y, getSize().x, getSize().y);
        gc.restore();
    }

    public void setParent(Entity e) {
        this.parent = e;
    }

    public Entity getParent() {
        return this.parent;
    }
}
