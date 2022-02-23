package student.adventure;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameEngine {
    File file = new File("src/main/resources/graingerLibrary.json"); //Deserialization
    AdventureGraingerLibraryDeserialization deserialize = new AdventureGraingerLibraryDeserialization(file);
    Layout gameLayout = deserialize.deserializeAdventureMap();

    GameOutput gameInterface = new GameOutput(); //!! Connecting to the GameOutput.java

    String presentRoom = gameLayout.getStartingRoom(); // runGame
    boolean quitGame = (presentRoom.equals(gameLayout.getEndingRoom())); //runGame

    public GameEngine() throws IOException {

    }

    /**
     * Function that examines the whole input commands
     * @param input
     */
    public void inputCommands(String input) {
        String directionInput;
        String itemInput;

        input = input.trim();

        if (input.split(" ")[0].equalsIgnoreCase("go") && input.split(" ").length == 2) {
            directionInput = input.split(" ")[1];
            validMove(directionInput);

        } else if (input.split(" ")[0].equalsIgnoreCase("take") && input.split(" ").length == 2) {
            itemInput = input.split(" ")[1];
            pickUpItem(itemInput);

        } else if (input.split(" ")[0].equalsIgnoreCase("drop") && input.split(" ").length == 2) {
            itemInput = input.split(" ")[1];
            releaseItem(itemInput);

        } else if (input.equalsIgnoreCase("examine")) {
            gameInterface.messagePrint(gameInterface.displayMessage());

        } else if (input.equalsIgnoreCase("history")) {
            gameInterface.messagePrint(gameInterface.displayTraversedRooms());
        } else {
            gameInterface.messagePrint("I don't understand " + input); // invalid command
        }

    }

    /**
     * starts the game
     */
    public void gameStart() {
        gameInterface.messagePrint(gameInterface.displayMessage());
        runGame(gameInterface.getInput());
    }

    /**
     * Runs the game until quitting
     * @param input
     */
    public void runGame(String input) {
        while (quitGame == false) {
            manualQuitGame(input);
            inputCommands(input);

            if (reachEndRoom() == true) {
                gameInterface.messagePrint("GAME OVER"); //displaying that the game has ended
                quitGame = true;
            } else {
                input = gameInterface.getInput();  // ** while loop & recursion is not good to have together **
            }
        }
    }

    /**
     * manually quits the game if wanted
     * @param input
     * @return quitGame
     */
    public boolean manualQuitGame(String input) {
        input = input.trim();

        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
            quitGame = true;
        }
        return quitGame;
    }

    /**
     * Shows when reached End Room
     * @return True
     */
    public boolean reachEndRoom() {
        String roomName = gameLayout.getRooms()[gameInterface.roomIndexPresent].getName();

        if (gameLayout.getEndingRoom().equalsIgnoreCase(roomName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates the move command and Moves the room
     * @param directionInput
     */
    public void validMove(String directionInput) {
        String room = "";
        boolean moveValidation = false;

        for (Directions direction : gameLayout.getRooms()[gameInterface.roomIndexPresent].getDirections()) {
            if (directionInput.equalsIgnoreCase(direction.getDirectionName())) {
                moveValidation = true;
                room = direction.getRoom();
                moveRooms(room);
            }
        }

        if (moveValidation == false) {
            gameInterface.messagePrint("I can't go " + directionInput); //Displaying not possible value
        }
    }

    /**
     * Helper function to move the room
     * @param roomInput
     */
    public void moveRooms(String roomInput) {
        for (Room room : gameLayout.getRooms()) {
            if (room.getName().equalsIgnoreCase(roomInput)) {
                gameInterface.roomIndexPresent = room.getRoomIndex();
            }
        }
        addRoomTraversed();
        gameInterface.messagePrint(gameInterface.displayMessage());
    }

    /**
     * picks up the item on the field
     * @param itemInput
     */
    public void pickUpItem(String itemInput) {
        boolean itemValid = false;
        for (Items item : gameLayout.getRooms()[gameInterface.roomIndexPresent].getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemInput)) {
                itemValid = true;
            }
        }

        if (itemValid == false) {
            gameInterface.messagePrint("There is no item \""  + itemInput + "\" in the room.");
        } else {
            gameLayout.getRooms()[gameInterface.roomIndexPresent].obtainItem(itemInput);
            gameInterface.player.obtainItem(itemInput);
        }
    }

    /**
     * Releases the item to the field
     * @param itemInput
     */
    public void releaseItem(String itemInput) {
        boolean itemValid = false;
        for (Items item : gameInterface.player.getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemInput)) {
                itemValid = true;
            }
        }

        if (itemValid == false) {
            gameInterface.messagePrint("You have no " + itemInput);
        } else {
            gameInterface.player.dropItem(itemInput);
            gameLayout.getRooms()[gameInterface.roomIndexPresent].dropItem(itemInput);
        }
    }

    /**
     * Adds the room traversed to a list
     */
    public void addRoomTraversed() {
        gameInterface.traversedRooms.add(gameInterface.gameLayout.getRooms()[gameInterface.roomIndexPresent].getName());
    }

    /**
     * getter function to get game Interface
     * @return
     */
    public GameOutput getGameInterface() {
        return gameInterface;
    }

    /**
     * setter function for the game Interface
     * @param gameInterface
     */
    public void setGameInterface(GameOutput gameInterface) {
        this.gameInterface = gameInterface;
    }
}
