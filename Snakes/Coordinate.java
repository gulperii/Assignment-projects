package game;

public class Coordinate {

private int x;
private int y;
private int type;

/**Constructor for Coordinate
 * @param x x coordinate
 * @param y y coordinate
 */
public Coordinate(int x,int y) {
	this.x=x;
	this.y=y;
	
}

/*
 * Getter for x coordinate
 * @return x coordinate
 */

public int getX() {
	return x;
}

/*
 * Setter for x coordinate
 * @param x value to set
 */
public void setX(int x) {
	this.x = x;
}

/*
 * Getter for y coordinate
 * @return y coordinate
 */
public int getY() {
	return y;
}

/*
 * Setter for y coordinate
 * @param y value to set
 */
public void setY(int y) {
	this.y = y;
}





}
