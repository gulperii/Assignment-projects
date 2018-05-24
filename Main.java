package main;

import game.Direction;
import game.Food;
import game.GameLogic;

import ui.ApplicationWindow;

import java.awt.*;

import game.Snake;


public class Main {

    /**
     * Main entry point for the application.
     * @param args application arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Create game
                // You can change the world width and height, size of each grid square in pixels or the game speed
            	
            	   GameLogic game = new GameLogic(30, 30, 15, 10);
                   Snake s=new Snake(4,1);
                   game.addSnake(s);
                                  
                    
                // Create application window that contains the game panel
                ApplicationWindow window = new ApplicationWindow(game.getGamePanel());
                window.getFrame().setVisible(true);

                // Start game
                game.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
