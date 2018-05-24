package game;

import java.awt.Color;
import ui.Drawable;
import ui.GridPanel;

public class Food extends Coordinate implements Drawable{
private int x;
private int y;

/**
 * Constructor to create food
 * @param x x coordinate of food
 * @param y y coordinate of food
 */
public Food(int x, int y) {
	super(x,y);
	
}

/**
 * Visualizes object on the UI.Draws a small filled square in the given grid position
 * @param panel GridPanel to draw on
 */
public void draw(GridPanel panel) {
	int x = this.getX();
	int y = this.getY();
	panel.drawSquare(x, y, Color.YELLOW);
	
}

}
