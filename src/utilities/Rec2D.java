package utilities;

public class Rec2D {
    private double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

    public double[] xPoints, yPoints;

    private double rotateX(double x, double y, double angle) {
        return x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
    }

    private double rotateY(double x, double y, double angle) {
        return x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
    }

    public Rec2D(double x, double y, double width, double height, double angle) {
        xPoints = new double[4];
        yPoints = new double[4];

        double cx = x + width / 2.0;
        double cy = y + height / 2.0;

        double tempX = x - cx;
        double tempY = y - cy;
//
//        minX = rotateX(tempX, tempY, angle) + cx;
//        minY = rotateY(tempX, tempY, angle) + cy;
//
//        tempX = (x + width) - cx;
//        tempY = (y + height) - cy;
//
//        maxX = rotateX(tempX, y - cy, angle) + cx;
//        maxY = rotateY(x - cx, tempY, angle) + cy;

        Vector2d[] points = new Vector2d[4];

        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                double xx = x + width * i - cx;
                double yy = y + height * j - cy;

                points[index] = new Vector2d(rotateX(xx, yy, angle) + cx, rotateY(xx, yy, angle) + cy);
                index++;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (points[i].x > maxX) maxX = points[i].x;
            if (points[i].x < minX) minX = points[i].x;
            if (points[i].y > maxY) maxY = points[i].y;
            if (points[i].y < minY) minY = points[i].y;

            this.xPoints[i] = points[i].x;
            this.yPoints[i] = points[i].y;
        }
    }


    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }
}
