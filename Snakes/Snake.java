package game;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import game.Action;
import ui.Drawable;
import ui.GridPanel;


public class Snake implements Drawable {
	public final int MAX_LENGTH=8;
    private int length;
	LinkedList<Coordinate> snakeBody=new LinkedList<Coordinate>(); ;

/**
 * Constructor for the first snake
 * snakeBody is the linkedlist that holds all body parts of the snake
 * @param x x coordinate of the head
 * @param y y coordinate of the head
 */
	public Snake(int x, int y) {
		length=4;
		snakeBody = createSnake(x,y);
	}
/**
 * Constructor for the snakes called by reproduce method
 */
	public Snake() {
		length=4;
		snakeBody = new LinkedList<Coordinate>(); 
	}

	/**
	 * Adds a snake to the game
	 * @param a snakes head's x location
	 * @param b snakes head's y location
	 * @return snakeBody of the snake
	 */
	public LinkedList createSnake(int a,int b) {
		Coordinate head = new Coordinate(a,b);
		snakeBody.add(head);
		int x = head.getX();
		int y = head.getY();
		for(int i=1;i<4;i++) {
			Coordinate tail = new Coordinate(x-i,y);
			snakeBody.add(tail);
		}
		return snakeBody;
	}

	/**
	 * @return the snakeBody
	 */
	public LinkedList<Coordinate> getSnakeBody() {
		return snakeBody;
	}
/*
 * adds a square to snake
 */
	public void grow(Coordinate tail) {
		snakeBody.add(tail);
	}

	/*
	 * increments length by 1
	 * adds one square to snake to where the food was
	 */
	public void eat(Coordinate food) {
		this.setLength(this.getLength()+1);
		this.snakeBody.add(0,food);
	}
	/**
	 * Visualizes object on the UI.Draws a small filled square in the given grid position
	 * @param panel GridPanel to draw on
	 */
	public void draw(GridPanel panel) {
		for(Coordinate cord: snakeBody) {
			panel.drawSquare(cord.getX(), cord.getY(), Color.BLUE);
		}int x = snakeBody.element().getX();
		int y = snakeBody.element().getY();
		panel.drawSquare(x, y, Color.RED);
	}


	/**
	 * Chooses the action that best fits to the situation. Checks certain conditions for each type of action.
	 * Doesn't execute that action
	 * @param info the information snake has about its environment
	 * @return chosen action
	 */
	public Action chooseAction(Environment info) {
		int x = info.getFoodInfo().getX();
		int y = info.getFoodInfo().getY();
		Coordinate food = new Coordinate(x,y);
		//if it reached the max. length it reproduces
		if(this.length==MAX_LENGTH) 
			return new Action(Action.Type.REPRODUCE);
		//if theres food at adjacent square it eats it
		else if(foodIsNear(info.getFoodInfo())) 
			return new Action(Action.Type.EAT);
		//if the direction to the food is clear, moves towards the food
		else if(this.randomDirection(food, info)!=null )
				return new Action(Action.Type.MOVE,randomDirection(food,info));	
		//if the road to the food is blocked ,it chooses a random free direction
		else if(this.randomDirection(food, info)==null && info.getFreeDirections()!=null)
			return new Action(Action.Type.MOVE,info.getRandomDirection(info.getFreeDirections()));
		// if there is no other choice it stays 
		else
			return new Action(Action.Type.STAY);
		
	}

/**
 * Returns if there is food next to the snakes head
 * @param foodLoc coordinate if tho food
 * @return true there is food
 */
public boolean foodIsNear(Coordinate foodLoc) {
	if(this.snakeBody.peekFirst().getX()-foodLoc.getX()==0&&Math.abs(this.snakeBody.peekFirst().getY()-foodLoc.getY())==1) {
		return true;
	}else if(this.snakeBody.peekFirst().getY()-foodLoc.getY()==0&&Math.abs(this.snakeBody.peekFirst().getX()-foodLoc.getX())==1) {
		return true;
	}else {
		return false;
	}
}	

/*
 * Moves the snake towards the given direction
 * @param dirrection to be moved
 */
public void move(Direction direction) {
	int x= snakeBody.peekFirst().getX();
	int y= snakeBody.peekFirst().getY();
	if (direction == Direction.UP) {
		y--;
	} else if (direction == Direction.DOWN) {
		y++;
	} else if (direction == Direction.LEFT) {
		x--;
	} else if (direction == Direction.RIGHT) {
		x++;
	}
	snakeBody.removeLast();
	Coordinate cord = new Coordinate(x,y);
	snakeBody.add(0,cord);
}

/**
 * Gives a direction towards food thats not occupied
 * @param food food to be move towards
 * @param info information that snake has about its surrounding
 * @return direction
 */
public Direction randomDirection(Coordinate food, Environment info) {
	int a =0;
	ArrayList<Direction> relativeDirection = new ArrayList<Direction>();
	int x = food.getX();
	int y = food.getY();
	int sx= snakeBody.getFirst().getX();
	int sy = snakeBody.getFirst().getY();
	if(sx>x) {
		relativeDirection.add(Direction.LEFT);
	}if(sx<x) {
		relativeDirection.add(Direction.RIGHT);
	}if(sy>y) {
		relativeDirection.add(Direction.UP);
	}if(sy<y) {
		relativeDirection.add(Direction.DOWN);
	}
	

	Random rand = new Random();
	int r = rand.nextInt(relativeDirection.size());		
	Direction dir = relativeDirection.get(r);
	
	// if the direction of food is occupied gives the other direction thats free.
	//if goes in loop, it returns null after 20 iteration 
	while(!info.getFreeDirections().contains(dir)) {
		a++;					
		r = rand.nextInt(relativeDirection.size());
		dir = relativeDirection.get(r);
		if(a==20) {
			return null;
		}
	}

	return dir;
}

// Divides into 2 snakes with length 4. New snakes head is old snakes tail
public Snake reproduce() {
	int x= this.snakeBody.peekLast().getX();
	int y= this.snakeBody.peekLast().getY();
	Snake nSnake= new Snake();
	Coordinate head = new Coordinate(x,y);
	nSnake.snakeBody.add(head);
	this.snakeBody.removeLast();
	for(int i=1;i<4;i++) {
		nSnake.grow(this.snakeBody.removeLast());
	}
	this.setLength(4);

	return nSnake;
}

/**
 * Getter for the length
 * @return length of the snake
 */
public int getLength() {
	return this.length;
}

/**
 * Setter for the length
 * @param length to be set
 */
public void setLength(int length) {
	this.length = length;
}

public void stay() {

} 

}
