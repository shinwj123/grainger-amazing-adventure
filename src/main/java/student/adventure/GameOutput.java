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
    List<String> traversedRooms = new ArrayList<>();

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

        if (itemList.length() > 0) {
            itemList = itemList.substring(0, itemList.length() - 2);
            itemList += ".";
        }

        return itemList;
    }

    /**
     * Display the item that player has
     * @return
     */
    public String displayPlayerItems() {
        String userItemList = "Your Items: ";

        for (Items item : player.getItems()) {
            userItemList += item.getItemName() + ", ";
        }

        if (userItemList.length() > 0) {
            userItemList = userItemList.substring(0, userItemList.length() - 2);
            userItemList += ".";
        }

        return userItemList;
    }

    /**
     * Displays the whole message of description, room options, items on field, items on hand.
     */
    public String displayMessage() {
        return displayRoomInformation() + "\n" + displayRoomOptionList() + "\n" + displayVisibleItems()
                + "\n" + displayPlayerItems();
    }

    public void messagePrint(String message) {
        System.out.println(message);
    }

    public String displayTraversedRooms() {
        String output = "";
        for (String roomName : traversedRooms) {
            output += roomName + ", ";
        }

        if (output.length() > 0) {
            output = output.substring(0, output.length() - 2);
            output += ".";
        }

        return "Rooms Traversed: " + output;
    }

    public String displayTraversedRoomsService() {
        String output = "";
        for (String roomName : traversedRooms) {
            output += roomName + ",";
        }

        return output;
    }

    public List<String> listRoomOptions() {
        List<String> roomOptions = new ArrayList<>();
        for (Directions directions : gameLayout.getRooms()[roomIndexPresent].getDirections()) {
            roomOptions.add(directions.getDirectionName());
        }
        return roomOptions;
    }

    public List<String> listPlayerItems() {
        List<String> playerItems = new ArrayList<>();
        for (Items items : player.getItems()) {
            playerItems.add(items.getItemName());
        }
        return playerItems;
    }


    public List<String> listRoomItems() {
        List<String> roomItems = new ArrayList<>();
        for (Items items : gameLayout.getRooms()[roomIndexPresent].getItems()) {
            roomItems.add(items.getItemName());
        }
        return roomItems;
    }
}
