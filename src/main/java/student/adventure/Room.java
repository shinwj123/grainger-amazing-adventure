package student.adventure;

import java.util.List;
import java.util.Locale;

public class Room {
    private String name;
    private String description;
    private Directions[] directions;
    private List<Items> items;
    private int roomIndex;

    public Room() {

    }

    public Room (String setName, String setDescription, Directions[] setDirections, List<Items> setItems, int setRoomIndex) {
        name = setName;
        description = setDescription;
        directions = setDirections;
        items = setItems;
        roomIndex = setRoomIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirections(Directions[] directions) {
        this.directions = directions;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Directions[] getDirections() {
        return directions;
    }

    public List<Items> getItems() {
        return items;
    }

    public int getRoomIndex() {
        return roomIndex;
    }

    public void obtainItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemName().equalsIgnoreCase(itemName)) {
                items.remove(i);
            }
        }
    }

    public void dropItem(String itemName) {
        Items item = new Items(itemName.toLowerCase(Locale.ROOT));
        items.add(item);
    }
}
