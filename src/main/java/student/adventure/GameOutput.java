package student.adventure;

import student.server.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameOutput {
    GameEngine gameEngine = new GameEngine();
    Layout gameLayout = gameEngine.gameLayout;

    Scanner scan = new Scanner(System.in);
    int roomIndexPresent = 0;

    List<Items> playerItemList = new ArrayList<>(); //pickup item, release item
    Player player = new Player(playerItemList,gameLayout.getStartingRoom());
    List<String> roomsTraversed = new ArrayList<>();

    public GameOutput() throws IOException {
    }

    /**
     * gets the input when player enters the input
     * @return input
     */
    public String getInput() {
        System.out.println(">>>");
        String input = scan.nextLine();
        return input;
    }

    /**
     * Display the room description
     * @return
     */
    public String displayRoomInformation() {
        String roomInformation = gameLayout.getRooms()[roomIndexPresent].getDescription();

        return roomInformation;
    }

    /**
     * Display the option of rooms that player can go
     * @return
     */
    public String displayRoomOptionList() {
        String roomOptions =  "From here, you can go: ";

        for (Directions direction : gameLayout.getRooms()[roomIndexPresent].getDirections()) {
            roomOptions +=  direction.getDirectionName() + ", ";
        }

        return roomOptions;
    }

    /**
     * Display the items in the current room
     * @return
     */
    public String displayVisibleItems() {
        String itemList = "Items visible: ";

        for (Items item : gameLayout.getRooms()[roomIndexPresent].getItems()) {
            itemList += item.getItemName() + ", ";
        }

        return itemList;
    }

    /**
     * Display the item that player has
     * @return
     */
    public String displayPlayerItems() {
        String playerItemList = "Your Items: ";

        for (Items item : player.getItems()) {
            playerItemList += item.getItemName() + ", ";
        }

        return playerItemList;
    }

    /**
     * Displays the whole message of description, room options, items on field, items on hand.
     */
    public void displayMessage() {
        System.out.println(displayRoomInformation() + "\n" + displayRoomOptionList() + "\n" + displayVisibleItems()
                + "\n" + displayPlayerItems());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCurrentRoomIndex() {
        return roomIndexPresent;
    }

    public List<String> getRoomsTraversed() {
        return roomsTraversed;
    }

    public void setRoomsTraversed(List<String> roomsTraversed) {
        this.roomsTraversed = roomsTraversed;
    }


}
