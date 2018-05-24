package game;
import ui.GridGame;
import game.Snake;
import game.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that implements the game logic for Snake.
 */
public class GameLogic extends GridGame {
	/**
	 * food is instance of food currently in the game
	 * Snakes keeps the list of snakes in the game
	 * gameMap keeps the information of each grid cell 
	 */
	private Food food;
	private List<Snake> Snakes;
	private Object[][] gameMap;

	/**
	 * Creates a new Snake game instance. Also an instance of food is created.
	 * @param gridWidth number of grid squares along the width
	 * @param gridHeight number of grid squares along the height
	 * @param gridSquareSize size of a grid square in pixels
	 * @param frameRate frame rate (number of timer ticks per second)
	 */
	public GameLogic(int gridWidth, int gridHeight, int gridSquareSize, int frameRate) {
		super(gridWidth, gridHeight, gridSquareSize, frameRate);
		food = createFood();
		Snakes = new ArrayList<>();
		gameMap = new Object[gridWidth][gridHeight];
		addFood(food);
	}
	/**
	 * Creates a new food in a random location
	 * @return food which is created
	 */
	public Food createFood() {
		int x = (int)(Math.random()*getGridWidth());
		int y= (int)(Math.random()*getGridHeight());	
		Food food = new Food(x,y);	
		return food;
	}
	/**
	 * Adds a new food to the game and make it visible
	 * @param food food to be added
	 * @return boolean indicating the success of addition
	 */
	public Boolean addFood(Food food) {
		int x =food.getX();
		int y =food.getY();
		addDrawable(food);
		gameMap[x][y]= food;
		return true;
	}


	@Override
	protected void timerTick() {
		// Determine and execute actions for all snakes
		ArrayList<Snake> snakesCopy = new ArrayList<>(Snakes);
		for (Snake snake : snakesCopy) {
			// Gets information about the environment of the snake 
			Environment info =createLocalInformationForSnake(snake);
			// Gets the action to be executed

			Action action = snake.chooseAction(info);
			// Reset current snake's map position 
			updateGameMap(snake,null);

			// Execute action
			if (action != null) {
				if (action.getType() == Action.Type.MOVE) {
					// MOVE	
					snake.move(action.getDirection());
					updateGameMap(snake,snake);
				} else if (action.getType() == Action.Type.EAT) {
					// EAT 
					snake.eat(createLocalInformationForSnake(snake).getFoodInfo());
					// Removes the food that has been eaten
					removeDrawable(food);
					//Adds a new food to the game 
					food = createFood();

					//Makes sure the food is created at a free location 
					while(gameMap[food.getX()][food.getY()]!=null) {
						food = createFood();
					}
					addFood(food);
					updateGameMap(snake,snake);
				} else if (action.getType() == Action.Type.REPRODUCE) {
					// REPRODUCE
					Snake nSnake = snake.reproduce();
					addSnake(nSnake);	
					updateGameMap(snake,snake);
				}else {
					//SNAKE
					snake.stay();
					updateGameMap(snake,snake);
				}

				updateGameMap(snake,snake);
			}
			snakesCopy = new ArrayList<>(Snakes);

		}
	}



	/**
	 * Adds a new snake to the game
	 * @param snake snake to be added
	 * @return boolean indicating the success of addition
	 */
	public boolean addSnake(Snake snake) {
		if (snake != null) {
			for(Coordinate cord : snake.getSnakeBody()) {
				int x= cord.getX();
				int y = cord.getY();
				if (isPositionInsideGrid(x, y)) {
					gameMap[x][y]= snake;
				}else {
					return false;
				}
			}
			Snakes.add(snake);
			addDrawable(snake);
			return true;	

		}
		return false;
	}

	/**
	 * Creates a list of free adjacent cells
	 * Creates a hashMap which contains object and its direction
	 * It also gets information for food
	 * Creates an instance of environment class with those informations.
	 * @param snake snake to be created information about 
	 * @return environment 
	 */
	private Environment createLocalInformationForSnake(Snake snake) {
		int x = snake.snakeBody.peekFirst().getX();
		int y = snake.snakeBody.peekFirst().getY();
		int fx = food.getX();
		int fy = food.getY();
		Coordinate foodLoc = new Coordinate(fx,fy);
		HashMap<Direction, Object> objects = new HashMap<>();
		objects.put(Direction.UP, getObjectAtPosition(x, y - 1));
		objects.put(Direction.DOWN, getObjectAtPosition(x, y + 1));
		objects.put(Direction.LEFT, getObjectAtPosition(x - 1, y));
		objects.put(Direction.RIGHT, getObjectAtPosition(x + 1, y));

		ArrayList<Direction> freeDirections = new ArrayList<>();
		if (!(objects.get(Direction.UP) instanceof Snake) && isPositionInsideGrid(x, y - 1)) {
			freeDirections.add(Direction.UP);
		}
		if (!(objects.get(Direction.DOWN) instanceof Snake)  && isPositionInsideGrid(x, y + 1)) {
			freeDirections.add(Direction.DOWN);
		}
		if (!(objects.get(Direction.LEFT) instanceof Snake) && isPositionInsideGrid(x - 1, y)) {
			freeDirections.add(Direction.LEFT);
		}
		if (!(objects.get(Direction.RIGHT) instanceof Snake)  && isPositionInsideGrid(x + 1, y)) {
			freeDirections.add(Direction.RIGHT);
		}

		return new Environment(getGridWidth(), getGridHeight(), objects, freeDirections,foodLoc);
	}
	/**
	 * Checks if the given position is inside the grid
	 */
	private boolean isPositionInsideGrid(int x, int y) {
		return (x >= 0 && x < getGridWidth()) && (y >= 0 && y < getGridHeight());
	}
	/**
	 * Updates the gameMap 
	 * Sets gameMap to null and iterates through the Snakes list and add their information to the gameMap
	 * @param info Environment instance to be used
	 */
	private void updateGameMap(Snake snake, Object object) {
		for(Coordinate cord : snake.snakeBody) {
			int x = cord.getX();
			int y= cord.getY();
			if (isPositionInsideGrid(x, y)) {
				gameMap[x][y] = object;
			}
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return the object at the given location
	 */
	private Object getObjectAtPosition(int x, int y) {
		if (!isPositionInsideGrid(x, y)) {
			return null;
		}
		return gameMap[x][y];
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @return the object at given direction
	 */

	private Object getObjectAtDirection(int x, int y, Direction direction) {
		if (direction == null) {
			return null;
		}
		int xTarget = x;
		int yTarget = y;
		if (direction == Direction.UP) {
			yTarget--;
		} else if (direction == Direction.DOWN) {
			yTarget++;
		} else if (direction == Direction.LEFT) {
			xTarget--;
		} else if (direction == Direction.RIGHT) {
			xTarget++;
		}
		return getObjectAtPosition(xTarget, yTarget);
	}
	/**
	 * Returns if the given location is free and inside the grid
	 * @param x
	 * @param y
	 * @return boolean
	 */
	private boolean isPositionFree(int x, int y) {
		return isPositionInsideGrid(x, y) && getObjectAtPosition(x, y) == null;
	}
	/**
	 * Returns if the given direction is free
	 * @param x
	 * @param y
	 * @param direction
	 * @return boolean
	 */
	private boolean isDirectionFree(int x, int y, Direction direction) {
		if (direction == null) {
			return false;
		}
		int xTarget = x;
		int yTarget = y;
		if (direction == Direction.UP) {
			yTarget--;
		} else if (direction == Direction.DOWN) {
			yTarget++;
		} else if (direction == Direction.LEFT) {
			xTarget--;
		} else if (direction == Direction.RIGHT) {
			xTarget++;
		}
		return isPositionFree(xTarget, yTarget);
	}

}
