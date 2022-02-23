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

    GameOutput gameInterface = new GameOutput();

    int roomIndexPresent = 0;

    Scanner scan = new Scanner(System.in); // getInput

    List<Items> playerItemList = new ArrayList<>(); //pickup item, release item
    Player player = new Player(playerItemList,gameLayout.getStartingRoom());

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

        }  else if (input.split(" ")[0].equalsIgnoreCase("take") && input.split(" ").length == 2) {
            itemInput = input.split(" ")[1];
            pickUpItem(itemInput);

        } else if(input.split(" ")[0].equalsIgnoreCase("drop") && input.split(" ").length == 2) {
            itemInput = input.split(" ")[1];
            releaseItem(itemInput);

        } else if (input.equalsIgnoreCase("examine")) {
            gameInterface.displayMessage();

        } else {
            System.out.println("I don't understand " + input); // invalid command
        }

    }

    /**
     * starts the game
     */
    public void gameStart() {
        gameInterface.displayMessage();
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
                System.out.println("GAME OVER"); //displaying that the game has ended
                quitGame = true;
            } else {
                runGame(gameInterface.getInput());
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
        String roomName = gameLayout.getRooms()[roomIndexPresent].getName();

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

        for (Directions direction : gameLayout.getRooms()[roomIndexPresent].getDirections()) {
            if (directionInput.equalsIgnoreCase(direction.getDirectionName())) {
                moveValidation = true;
                room = direction.getRoom();
                moveRooms(room);
            }
        }

        if (moveValidation == false) {
            System.out.println("I can't go " + directionInput); //Displaying not possible value
        }
    }

    /**
     * Helper function to move the room
     * @param roomInput
     */
    public void moveRooms(String roomInput) {
        for (Room room : gameLayout.getRooms()) {
            if (room.getName().equalsIgnoreCase(roomInput)) {
                roomIndexPresent = room.getRoomIndex();
            }
        }
    }

    /**
     * picks up the item on the field
     * @param itemInput
     */
    public void pickUpItem(String itemInput) {
        boolean itemValid = false;
        for (Items item : gameLayout.getRooms()[roomIndexPresent].getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemInput)) {
                itemValid = true;
            }
        }

        if (itemValid == false) {
            System.out.println("There is no item \""  + itemInput + "\" in the room.");
        } else {
            gameLayout.getRooms()[roomIndexPresent].obtainItem(itemInput);
            player.obtainItem(itemInput);
        }
    }

    /**
     * Releases the item to the field
     * @param itemInput
     */
    public void releaseItem(String itemInput) {
        boolean itemValid = false;
        for (Items item : player.getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemInput)) {
                itemValid = true;
            }
        }

        if (itemValid == false) {
            System.out.println("You have no " + itemInput);
        } else {
            player.dropItem(itemInput);
            gameLayout.getRooms()[roomIndexPresent].dropItem(itemInput);
        }
    }
}
