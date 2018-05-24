package project;


import game.Direction;
import game.Drawable;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;

public abstract class Creature implements Drawable {
	protected double health;
	protected int x;
	protected int y;
	/**
	 * Constructor for Creature 
	 * @param x x coordinate of the object
	 * @param y y coordinate of the object
	 */
	public Creature(int x, int y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * @return x coordinate of the creature
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return y coordinate of the creature
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return health of the creature
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * Sets the health of the creature to the given value and also checks for the valid range. If given a negative value sets health to 0
	 * @param health health value to set
	 * @param maxHealth highest value of health 
	 */
	public void setHealth(double health,double maxHealth) {
		if(health>=maxHealth) 
			this.health=maxHealth;
		else if(health<=0)
			this.health=0;
		else
			this.health=health;
	}

	/**
	 * Visualizes object on the UI.Draws a small filled square in the given grid position
	 * @param panel GridPanel to draw on
	 */
	abstract public void draw(GridPanel panel) ;

	/**
	 * Chooses the action that best fits to the situation. Checks certain conditions for each type of action.
	 * Doesn't execute tha action
	 * @param info the information creature has about its environment
	 * @return chosen action
	 */
	abstract public Action chooseAction(LocalInformation info);

	/**
	 * Creates a new creature of the same type after checking the surrounding & health of the creature
	 * @param direction the direction to which the creature will reproduce
	 * @returns Creature creature that has been 
	 */
	abstract public Creature reproduce(Direction direction);

	/**
	 * If no action is possible creature stays at the same position
	 */
	abstract public void stay();

	/**
	 * Attacks the creature at adjacent square and moves to the position of the attacked creature.
	 * Also set attacked creature's health to 0.
	 * @param creature creature to be attacked
	 */
	public void attack(Creature creature) {

	}
	/**
	 * Moves creature to a free adjacent square
	 * @param direction the direction to which the creature will move
	 */

	public void move (Direction direction) {

	}
}
