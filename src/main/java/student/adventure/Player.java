package student.adventure;

import java.util.List;
import java.util.Locale;

public class Player {
    String room;
    List<Items> items;

    public Player() {

    }

    public Player (List<Items> setItems, String setRoom) {
        items = setItems;
        room = setRoom;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public String getRoom() {
        return room;
    }

    public List<Items> getItems() {
        return items;
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
