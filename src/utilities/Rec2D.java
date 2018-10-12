package utilities;

public class Rec2D {
    private double x, y, width, height, angle;

    private double minX, minY, maxX, maxY;

    public Rec2D(double x, double y, double width, double height, double angle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;

        double cx = x + width  / 2;
        double cy = y - height / 2;

        // translate point to origin
        double tempX = x - cx;
        double tempY = y - cy;

         // now apply rotation
        double rotatedX = tempX * Math.cos(angle) - tempY * Math.sin(angle);
        double rotatedY = tempX * Math.sin(angle) + tempY * Math.cos(angle);

         // translate back
        minX = rotatedX + cx;
        maxY = rotatedY + cy;
    }


}
