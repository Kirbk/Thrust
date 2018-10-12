import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import utilities.Vector2f;

public class Entity {
    private Image icon;
    private Vector2f position;
    private Vector2f size;
    private Vector2f velocity;
    private double rotationVelocity = 0.0;
    private double angle = 0.0;
    private double drawAngle = 0.0;
    private boolean hidden = false;
    private boolean remove = false;

    Entity(Image icon, Vector2f position, Vector2f size, double angle) {
        this.icon = icon;
        this.position = position;
        this.size = size;
        this.velocity = new Vector2f(0.0f, 0.0f);
        this.angle = angle;
    }

    Entity(Image icon, Vector2f position, Vector2f size) {
        this.icon = icon;
        this.position = position;
        this.size = size;
        this.velocity = new Vector2f(0.0f, 0.0f);
    }

    protected void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    void draw(GraphicsContext gc, Vector2f camPosition) {
        gc.save();
        rotate(gc, drawAngle + angle, position.x - camPosition.x + size.x / 2, position.y - camPosition.y + size.y / 2);
        gc.drawImage(icon, position.x - camPosition.x, position.y - camPosition.y, size.x, size.y);
        gc.restore();
    }

    void update(double deltaT) {
        if (velocity.y >= World.TERMINAL_VELOCITY) {
            velocity.y *= 0.99;
        } else {
            changeVelY(World.GRAVITY);
        }

        setRotationVelocity(rotationVelocity * 0.9);
        changeAngle(rotationVelocity * deltaT);

        changeX((float)(velocity.x * deltaT));
        changeY((float)(velocity.y * deltaT));
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(getPosition().x, getPosition().y, getSize().x, getSize().y);
    }

    public boolean intersects(Entity e)
    {
        return e.getBoundary().intersects(this.getBoundary());
    }

    public void setRotationVelocity(double rotationVelocity) {
        this.rotationVelocity = rotationVelocity;
    }

    void changeRotationVelocity(double rotationVelocity) {
        this.rotationVelocity += rotationVelocity;
    }

    public void setX(float x) {
        this.position.x = x;
    }

    public void setY(float y) {
        this.position.y = y;
    }

    public void changeX(float x) {
        this.position.x += x;
    }

    public void changeY(float y) {
        this.position.y += y;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void changeAngle(double angle) {
        this.angle += angle;
    }

    public void setDrawAngle(double drawAngle) {
        this.drawAngle = drawAngle;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void setVelX(float velX) {
        this.velocity.x = velX;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void changeVelX(float velX) {
        this.velocity.x += velX;
    }

    public void setVelY(float velY) {
        this.velocity.y = velY;
    }

    public void setRemove(boolean remove) { this.remove = remove; }

    public void changeVelY(float velY) {
        this.velocity.y += velY;
    }

    public Image getIcon() {
        return this.icon;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getDrawAngle() {
        return this.drawAngle;
    }

    public Vector2f getVelocity() {
        return this.velocity;
    }

    public double getRotationVelocity() {
        return this.rotationVelocity;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public boolean getRemove() { return remove; }
}
