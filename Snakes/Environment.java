package game;

import game.Direction;
import java.util.HashMap;
import java.util.List;


/**
 * Class representing the information a snake has about its surroundings.
 * Automatically created and passed by the game to each snake at each timer tick.
 */
public class Environment {

    private int gridWidth;
    private int gridHeight;
    private HashMap<Direction, Object> objects;
    private List<Direction> freeDirections;
    private Coordinate foodLoc;

    /**
     * Constructs the environment information for a snake
     * @param gridWidth width of the grid world
     * @param gridHeight height of the grid world
     * @param objects mapping of directions to neighbor snakes
     * @param freeDirections list of free directions
     * @param foodLoc food's coordinates
     */
    Environment(int gridWidth, int gridHeight,
                     HashMap<Direction, Object> objects, List<Direction> freeDirections,Coordinate foodLoc) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.objects = objects;
        this.freeDirections = freeDirections;
        this.foodLoc = foodLoc;
    }
    /**
     * Getter for the food's location
     * @return foods location
     */
    
    public Coordinate getFoodInfo() {
        return foodLoc;
    }

    /**
     * Getter for the width of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the width
     */
    public int getGridWidth() {
        return gridWidth;
    }
    /**
     * Getter for the height of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the height
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Returns the neighbor object one square up
     * @return snake or null if no snake exists
     */
    public Object getObjectUp() {
        return objects.get(Direction.UP);
    }

    /**
     * Returns the neighbor object one square down
     * @return snake or null if no snake exists
     */
    public Object getObjectDown() {
        return objects.get(Direction.DOWN);
    }

    /**
     * Returns the neighbor object one square left
     * @return snake or null if no snake exists
     */
    public Object getObjectLeft() {
        return objects.get(Direction.LEFT);
    }

    /**
     * Returns the neighbor object one square right
     * @return snake or null if no snake exists
     */
    public Object getObjectRight() {
        return objects.get(Direction.RIGHT);
    }

    /**
     * Returns the list of free directions around the current position.
     * The list does not contain directions out of bounds or containing a snake.
     * Can be used to determine the directions available to move or reproduce.
     * @return object or null if no creature exists
     */
    public List<Direction> getFreeDirections() {
        return freeDirections;
    }

    /**
     * Utility function to get a randomly selected direction among multiple directions.
     * The selection is uniform random: All directions in the list have an equal chance to be chosen.
     * @param possibleDirections list of possible directions
     * @return direction randomly selected from the list of possible directions
     */
    public static Direction getRandomDirection(List<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return null;
        }
        int randomIndex = (int)(Math.random() * possibleDirections.size());
        return possibleDirections.get(randomIndex);
    }

}
