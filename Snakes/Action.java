package game;

import game.Direction;


/**
 * Class representing the possible actions 
 */
public class Action {

    /**
     * Enum representing possible action types
     */
    public enum Type {
        MOVE,
        REPRODUCE,
        EAT,
        STAY,
    }
    private final Direction direction;
    private final Type type;
   
    /**
     * Creates an action without direction
     * @param type action type
     */
    public Action(Type type) {
       direction = null;
        this.type = type;
       
    }

    /**
     * Creates an action with direction
     * @param type action type
     * @param direction direction
     **/
    public Action(Type type,Direction direction) {
        this.direction =direction;
        this.type = type;
       
    }
    /**
     * Getter for the type of the action
     * @return action type
     */
    public Type getType() {
        return type;
    }

    /**
     * Getter for the direction of the action
     * @return direction or null if a direction does not exist
     */
   
    public Direction getDirection() {
        return direction;
    }

}
