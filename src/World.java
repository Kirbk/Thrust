import javafx.scene.canvas.GraphicsContext;
import utilities.Vector2f;

import java.util.ArrayList;

public class World {
    public static float GRAVITY = 8f;
    public static float TERMINAL_VELOCITY = 500;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> removeList;

    private boolean paused = false;

    public void setGravity(float gravity) {
        GRAVITY = gravity;
    }

    public void setTerminalVelocity(float terminalVelocity) {
        TERMINAL_VELOCITY = terminalVelocity;
    }

    public boolean getPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public World() {
        entities = new ArrayList<>();
        removeList = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void clearEntities() {
        entities.clear();
    }

    public void drawEntities(GraphicsContext gc, Vector2f camPosition) {
        int drawCount = 0;
        for (Entity e : entities) {
            double maxLength = Math.sqrt(Math.pow(e.getSize().x, 2) + Math.pow(e.getSize().y, 2));

            if (e.getPosition().x + maxLength  - camPosition.x < 0 || e.getPosition().x - camPosition.x > Main.getScreenWidth() || e.getPosition().y + maxLength - camPosition.y < 0 || e.getPosition().y - camPosition.y > Main.getScreenHeight()) {
                e.setHidden(true);
            } else {
                e.setHidden(false);
                drawCount++;
            }

            if (!e.getHidden())
                e.draw(gc, camPosition);

            gc.strokeRect(e.getBoundary().getMinX() - camPosition.x, e.getBoundary().getMinY() - camPosition.y, e.getBoundary().getWidth(), e.getBoundary().getHeight());
        }

        gc.fillText("Entities: " + String.valueOf(getEntities().size()) + "(" + String.valueOf(drawCount) + ")", 10, 44);
    }

    public void updateEntities(double deltaT) {
        if(!paused) {
            for (Entity e : entities) {
                if (!(e instanceof Player)) {
                    e.update(deltaT);

                    if (e.getRemove()) removeList.add(e);
                }
            }

            for (Entity e : removeList) {
                entities.remove(e);
            }

            removeList.clear();
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
