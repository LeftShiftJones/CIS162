import java.util.ArrayList;
/******************************************************************
 * Game contains all necessary methods to play the game
 *
 * @author [Ryan A. Jones]
 * @version [1.0, 12/08/2015]
 ******************************************************************/
public class Game{

    /** All Rooms */
    private Room currentRoom, planeSite, planeWreck, cliff, cliffDeath,
    caveDeath, forestOne, forestTwo, forestThree, beachOne, beachTwo,
    lake, caveEntrance, caveTunnelOne, caveTunnelTwo, caveTunnelEnd,
    caveLake, caveLakeTwo, cockpit;

    /** Items */
    private Item wood, water, coconut, apple, vines, rock, tarp, rope,
    compass, raft;

    /** ArrayList Inventory */
    private ArrayList<Item> inventory;

    /** Message */
    private String msg;

    /*******************************************************************
     * Constructor Initializes all methods and variables
     *******************************************************************/
    public Game(){

        //create rooms, set default text
        createRooms();
        msg = "";
        currentRoom = planeSite;
        setWelcomeMessage();
    }

    /*******************************************************************
     * @return adjacent room in direction direction
     *******************************************************************/
    public Room getNeighbor(String direction){
        Room isNextRoom = currentRoom.getNeighbor(direction);
        return isNextRoom;
    }

    /*******************************************************************
     * Method to move player
     *
     * @param direction Direction that player will move in
     *******************************************************************/
    public void move(String direction){
        Room nextRoom = currentRoom.getNeighbor(direction);
        if(nextRoom == null){
            msg = "You can't go that way.";
        } else{
            currentRoom = nextRoom;
            msg = currentRoom.getLongDescription();
        }
    }

    /*******************************************************************
     * @return string message
     *******************************************************************/
    public String getMessage(){
        msg += "\n\n";
        return msg;
    }

    /*******************************************************************
     * Sets message to room description
     *******************************************************************/
    public void look(){
        msg = currentRoom.getLongDescription();
    }

    /*******************************************************************
     * Method indicates game over parameters
     * @return boolean of game over value
     *******************************************************************/
    public boolean gameOver(){
        if(currentRoom == beachOne && currentRoom.getItem() == raft){
            return true;
        } else if(currentRoom == cliffDeath || currentRoom == caveDeath){
            return true;
        } else{
            return false;
        }
    }

    /*******************************************************************
     * Pickup method puts room item in player's inventory
     *******************************************************************/
    public void pickup(){
        if(currentRoom.hasItem()){
            inventory.add(currentRoom.getItem());
        } else{
            msg = "There is nothing to pick up";
        }
    }

    /*******************************************************************
     * Method that drops item into current room.
     * @param str String to check against Items in inventory
     *******************************************************************/
    public void drop(String str){
        Item drop = checkForItem(str);

        //if item doesn't exist in inventory
        if(drop == null){
            msg = "You aren't carrying that item.";
            return;
        }

        //all other cases
        if(currentRoom.hasItem()){
            //if the room already has an item
            msg = "You cannot drop that here, there is already an Item.";
        } else{
            currentRoom.addItem(drop);
            inventory.remove(drop);
        }
    }

    private void setWelcomeMessage(){
        msg = "Welcome to Ryan's Adventure game: Survival Island!\n\n";
        msg += "You are trapped on an island after a plane crash.\n\n";
        msg += "You need to build a raft to escape the island.";
    }

    public Item checkForItem(String str){
        str = str.toLowerCase();
        for(Item i:inventory){
            if(i.getName().contains(str)){
                return i;
            }
        }
        return null;
    }

    public void eat(String str){
        Item eat = checkForItem(str);
        if(eat == null){
            return;
        }

        if(eat.isEdible()){
            inventory.remove(eat);
            msg = "You have eaten the " + eat.getName();
        } else{
            msg = "You can't eat that.";
        }
    }

    public void craft(){
        Item itemOne, itemTwo, itemThree;
        itemOne = checkForItem("rope");
        itemTwo = checkForItem("wood");
        itemThree = checkForItem("tarp");
        if(itemOne != null && itemTwo != null && itemThree != null){
            inventory.remove(itemOne);
            inventory.remove(itemTwo);
            inventory.remove(itemThree);
            msg = "You have crafted the raft";
        } else{
            msg = "You can't craft the raft yet.";
        }
    }

    public void drink(String str){
        Item drink = checkForItem(str);
        if(drink == null){
            return;
        }

        if(drink.isDrinkable()){
            inventory.remove(drink);
            msg = "You drank the " + drink.getName();
        } else{
            msg = "You can't drink that.";
        }
    }

    /*******************************************************************
     * Displays helpful information about the game, including available
     * moves.
     *******************************************************************/
    public void help(){
        String[] direction = {"north","south","east","west"};
        //set msg to help
        if(checkForItem("raft") == null){
            msg = "Objective: Collect Materials and craft the raft.";
        } else{
            msg = "Objective: Escape from the beach.";
        }

        msg += "Available moves: \n";
        for(String s:direction){
            if(currentRoom.getNeighbor(s) == null){
                continue;
            } else{
                msg += direction + "\n";
            }
        }
    }

    /*******************************************************************
     * Lists items in inventory
     *******************************************************************/
     public void list(){
        msg = "You are holding ";
        if(inventory.size() == 0){
            msg += "nothing.";
        } else{
            for(Item i:inventory){
                msg += i.getDescription() + "\n";
            }
        }
    }

    /*******************************************************************
     * Initializes variables
     *******************************************************************/
    public void createRooms(){

        //initialize items
        wood = new Item("wood", " some wood planks.", 10, false, false);
        water = new Item("water bottle", " a water bottle.", 2, false, true);
        coconut = new Item("coconut", " a coconut.", 5, true, true);
        apple = new Item("apple", " an apple.", 5, true, false);
        vines = new Item("vines", " some vines.", 15, false, false);
        rock = new Item("rock", " an enormous rock.", 500, false, false);
        tarp = new Item("tarp", " a large tarp.", 10, false, false);
        rope = new Item("rope", " some rope", 17, false, false);
        compass = new Item("compass", " a working compass", 4, false, false);

        //initialize rooms
        planeSite = new Room(" in a small clearing. Nearby is the wreckage of " +
            "your plane.");
        planeWreck = new Room(" in the hull of your aircraft.", water);
        cockpit = new Room(" in the cockpit of your downed plane. "+
            "Most of the instruments are uselsess.", compass);
        forestOne = new Room("on a narrow path in a dense forest.", apple);
        forestTwo = new Room("in a forest of tall Redwood trees.");
        forestThree = new Room("in a field of tall bushes.");
        beachOne = new Room("on the shore of a sandy beach.", coconut);
        beachTwo = new Room("on a sandy beach.", rock);
        lake = new Room("in an open field, standing near a freshwater lake.",
            tarp);
        cliff = new Room("on the edge of a 100-foot cliff. It's a long way down...");
        caveEntrance = new Room("standing at the entrance of a cave.");
        caveTunnelOne = new Room("in one of the cave's many tunnels. "+
            "You can see the light of the cave entrance");
        caveTunnelTwo = new Room("deep in the cave. It is extremely dark.");
        caveLake = new Room("deep in the cave. You stand before a giant underground lake.");
        caveLakeTwo = new Room("swimming underneath the lake.");
        caveTunnelEnd = new Room("at the end of the tunnel. No way to go but back.");
        caveDeath = new Room("dead. You suffocated in a wall.");
        cliffDeath = new Room("dead. You can't fly, idiot.");

        //set Neighbors
        planeSite.addNeighbor("west", planeWreck);
        planeSite.addNeighbor("east", forestOne);
        planeWreck.addNeighbor("east", planeSite);
        planeWreck.addNeighbor("north", cockpit);
        cockpit.addNeighbor("south", planeWreck);

        forestOne.addNeighbor("north", lake);
        forestOne.addNeighbor("west", planeSite);
        forestOne.addNeighbor("east", forestTwo);
        forestTwo.addNeighbor("west", forestOne);
        forestTwo.addNeighbor("south", beachTwo);
        forestThree.addNeighbor("east", caveEntrance);
        beachTwo.addNeighbor("north", forestTwo);
        beachTwo.addNeighbor("west", beachOne);
        beachOne.addNeighbor("east", beachTwo);
        lake.addNeighbor("north", caveEntrance);
        lake.addNeighbor("south", forestOne);
        caveEntrance.addNeighbor("north", caveTunnelOne);
        caveEntrance.addNeighbor("west", forestThree);
        caveEntrance.addNeighbor("south", lake);
        caveTunnelOne.addNeighbor("south", caveEntrance);
        caveTunnelOne.addNeighbor("east", caveTunnelTwo);
        caveTunnelTwo.addNeighbor("west", caveTunnelOne);
        caveTunnelTwo.addNeighbor("north", caveTunnelEnd);
        caveTunnelTwo.addNeighbor("east", cliff);
        cliff.addNeighbor("west", caveTunnelTwo);
        cliff.addNeighbor("east", cliffDeath);
        caveTunnelEnd.addNeighbor("north", caveDeath);
        caveTunnelEnd.addNeighbor("south", caveTunnelTwo);
    }

}
