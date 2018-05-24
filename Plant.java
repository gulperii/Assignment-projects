package project;

import java.awt.Color;

import game.*;
import naturesimulator.*;
import ui.GridPanel;


public class Plant extends Creature implements Drawable{
	protected final double MAX_HEALTH = 1; 
	/**
	 * Creates a new Plant instance
	 * @param x x coordinate of the plant
	 * @param y y coordinate of the plant
	 */
	public Plant (int x, int y) {
		super(x,y);
		health=0.5;

	}
	/**
	 * {@inheritDoc}
	 * For Plant there are 2 types of action to be executed
	 */
	public Action chooseAction(LocalInformation info) {
		if(health>=0.75&&!info.getFreeDirections().isEmpty()) 
			return new Action(Action.Type.REPRODUCE, LocalInformation.getRandomDirection(info.getFreeDirections()));
		else 
			return new Action(Action.Type.STAY);
	}
	
	public void draw(GridPanel panel) {
			panel.drawSquare(x, y, Color.GREEN);
	}

	public Creature reproduce(Direction direction) {
		int x1=this.x;
		int y1=this.y;
		if (direction == Direction.UP) {
			y1--;
		} else if (direction == Direction.DOWN) {
			y1++;
		} else if (direction == Direction.LEFT) {
			x1--;
		} else if (direction == Direction.RIGHT) {
			x1++;
		}
		Plant offspring = new Plant(x1,y1);
		offspring.setHealth(this.health*0.1,this.MAX_HEALTH);
		this.setHealth(this.health*0.7,this.MAX_HEALTH);
		return offspring;
	}
	public void stay() {
		setHealth(this.getHealth()+0.05,this.MAX_HEALTH);
	}


}
