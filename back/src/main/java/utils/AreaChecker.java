package utils;

public class AreaChecker {
    public static boolean checkHit(double x, double y, double r) {
        boolean inRectangle = (x >= 0) && (y >= 0) && (x <= r/2) && (y <= r);
        boolean inTriangle = (x <= 0) && (y >= 0) && (y <= x + 1);
        boolean inCircle = (x >= 0) && (y <= 0) && (x * x + y * y <= (r / 2) * (r / 2));
        return inRectangle || inTriangle || inCircle;
    }

}
