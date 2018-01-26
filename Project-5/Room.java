import java.util.HashMap;
/******************************************************************
 * Class room, holds an Item and allows player to move about the
 * map using the hashmap class
 *
 * @author [Ryan A. Jones]
 * @version [1.0, 12/08/2015]
 ******************************************************************/
public class Room{

    /** Hashmap of rooms */
    private HashMap <String, Room> myNeighbors;

    /** Item in room */
    private Item roomItem;

    /** Description of room */
    private String description;

    /******************************************************************
     * Constructor initializes variables
     * @param d Sets room description
     * @param i Sets room Item
     ******************************************************************/
    public Room(String d, Item i){
        description = d;
        roomItem = i;
        myNeighbors = new HashMap<String, Room>();
    }

    /******************************************************************
     * OverloadingConstructor
     ******************************************************************/
    public Room(String d){
        description = d;
        roomItem = null;
        myNeighbors = new HashMap<String, Room>();
    }

    /******************************************************************
     * Used to drop an item into the current room
     * @param newItem Item to be droppped
     ******************************************************************/
    public void addItem(Item newItem){
        if(!hasItem()){
            roomItem = newItem;
        }
    }

    /******************************************************************
     * @return Boolean depending on existance of item in current room
     ******************************************************************/
    public boolean hasItem(){
        if(roomItem!=null){
            return true;
        } else{
            return false;
        }
    }

    /******************************************************************
     * Return description of current room
     * @return methodDescription A local variable made to display the
     * room description
     ******************************************************************/
    public String getLongDescription(){

        //string to return
        String methodDescription = "You are " + description;

        //if room has an item, return it
        if(hasItem()){
            methodDescription += "\nYou See " + roomItem.getName();
        }
        return methodDescription;
    }

    /******************************************************************
     * Adds a room to Room HashMap
     * @param direction Direction of new neighbor
     * @param newRoom Room placed in direction related to current room
     ******************************************************************/
    public void addNeighbor(String direction, Room newRoom){
        myNeighbors.put(direction, newRoom);
    }

    /******************************************************************
     * @return room if direction is valid
     ******************************************************************/
    public Room getNeighbor(String direction){
        Room isNextRoom = myNeighbors.get(direction);
        return isNextRoom;
    }

    /******************************************************************
     * Method used to pickup items, removes item from room.
     * @return tempItem if item is in the room
     * @return null if item does not exist
     ******************************************************************/
    public Item removeItem(){
        if(hasItem()){
            Item tempItem = roomItem;
            roomItem = null;
            return tempItem;
        } else{
            return null;
        }
    }

    /******************************************************************
     * @return roomItem
     ******************************************************************/
    public Item getItem(){
        return roomItem;
    }
}
