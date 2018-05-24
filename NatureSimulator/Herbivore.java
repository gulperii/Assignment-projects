package project;

import java.awt.Color;
import java.util.ArrayList;
import game.*;
import naturesimulator.*;
import ui.GridPanel;

public class Herbivore extends Creature implements Drawable{
	protected final double MAX_HEALTH = 20;

	/**
	 * Constructor for Herbivore
	 * @param x x coordinate of the object
	 *  @param y y coordinate of the creature  
	 */

	public Herbivore(int x, int y) {
		super(x,y);
		health = 10.0;
	}

	/**
	 * Makes a list of directions of the adjacent squares that have plant on it 
	 * Returns a randomly selected direction from the list.
	 * Can be used to determine the directions available to attack or to stay if no plant is available
	 * @return direction or null if no plant exists
	 */

	public Direction randomPlantDirection(LocalInformation information) {
		ArrayList<Direction> randomPlant = new ArrayList<>();
		if(information.getCreatureDown() instanceof Plant)
			randomPlant.add(Direction.DOWN);
		if(information.getCreatureUp() instanceof Plant)
			randomPlant.add(Direction.UP);
		if(information.getCreatureRight() instanceof Plant)
			randomPlant.add(Direction.RIGHT);
		if(information.getCreatureLeft() instanceof Plant)
			randomPlant.add(Direction.LEFT);
		if(!randomPlant.isEmpty()) {
			int randomIndex = (int)(Math.random() * randomPlant.size());
			return randomPlant.get(randomIndex);
		}
		return null;
	}
	/**
	 * {@inheritDoc}
	 * For herbivore there are 4 types of action to be executed
	 */
	public Action chooseAction(LocalInformation info) {
		if(health==MAX_HEALTH&&!info.getFreeDirections().isEmpty()) {
			return new Action(Action.Type.REPRODUCE, LocalInformation.getRandomDirection(info.getFreeDirections()));}
		else if(randomPlantDirection(info)!=null ) {
			return new Action(Action.Type.ATTACK,randomPlantDirection(info));}
		else if(randomPlantDirection(info)==null&&this.getHealth()-1>0) {
			return new Action(Action.Type.MOVE,LocalInformation.getRandomDirection(info.getFreeDirections()));}
		else 
			return new Action(Action.Type.STAY);
	}

	public Creature reproduce(Direction direction) {
		double initialHealth= this.getHealth();
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
		Herbivore offspring = new Herbivore(x1,y1);
		offspring.setHealth(initialHealth*0.2,this.MAX_HEALTH);
		this.setHealth(initialHealth*0.4,this.MAX_HEALTH);
		return offspring;
	}
	public void attack(Creature attacked) {
		if(attacked instanceof Plant) {
			this.setHealth(attacked.health+this.health,this.MAX_HEALTH);
			attacked.setHealth(0,1);
			this.x=attacked.x;
			this.y=attacked.y;
		}
	}
	public void stay() {
		this.setHealth(health-0.1,MAX_HEALTH);

	}

	public void move(Direction direction) {
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
		this.setHealth(getHealth()-1,MAX_HEALTH);
	}
	public void draw(GridPanel panel) {
		panel.drawSquare(x, y, Color.RED);
	}
}

