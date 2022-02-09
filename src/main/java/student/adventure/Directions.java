package student.adventure;

public class Directions {
    private String directionName;
    private String room;

    public Directions(){

    }

    public Directions(String setDirectionName, String setRoom) {
        directionName = setDirectionName;
        room = setRoom;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getRoom() {
        return room;
    }

    public String getDirectionName() {
        return directionName;
    }
}
