import java.awt.*;
import javax.swing.*;

public class ShapeIcon implements Icon {
    public enum Shape { TRIANGLE, DIAMOND, CIRCLE, SQUARE }
    private Shape shape;
    private int width, height;
    private Color color;

    public ShapeIcon(Shape shape, int width, int height, Color color) {
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        switch (shape) {
            case TRIANGLE:
                int[] tx = {x + width / 2, x, x + width};
                int[] ty = {y, y + height, y + height};
                g.fillPolygon(tx, ty, 3);
                break;
            case DIAMOND:
                int[] dx = {x + width / 2, x, x + width / 2, x + width};
                int[] dy = {y, y + height / 2, y + height, y + height / 2};
                g.fillPolygon(dx, dy, 4);
                break;
            case CIRCLE:
                g.fillOval(x, y, width, height);
                break;
            case SQUARE:
                g.fillRect(x, y, width, height);
                break;
        }
    }

    @Override
    public int getIconWidth() { return width; }
    @Override
    public int getIconHeight() { return height; }
}