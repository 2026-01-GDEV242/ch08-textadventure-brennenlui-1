import java.util.ArrayList;
/**
 * This class represents the player in the "World of Zuul" game.
 * The player has a current room, an inventory of item, and a maxiumum carry weight
 *
 * @author Brennen Lui
 * @version 2026.04.13
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private int maxWeight;
    private ArrayList<Item> inventory;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room startRoom, int maxWeight)
    {
        currentRoom = startRoom;
        this.maxWeight = maxWeight;
        inventory = new ArrayList<>();
    }

    /**
     * Return the room the player is currently in
     * @return the player's current room
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    /**
     * Set the player's current room
     * @param room The new room
     */
    public void setCurrentRoom(Room room){
        currentRoom = room;    
    }
    
    /**
     * Return the total weight of all items currently being carried
     * @return The total weight of the items being carried
     */
    public int getCurrentCarryWeight(){
        int total = 0;
        for (Item item : inventory){
            total += item.getWeight();
        }
        return total;
    }
    
    /**
     * Picks up the item and adds it to the players inventory if it does not exceed the maximum carry weight
     * @param item The item to pick up
     * @return True if the item was picked up, otherwise false
     */
    public boolean pickUpItem(Item item){
        if (getCurrentCarryWeight() + item.getWeight() <= maxWeight){
            inventory.add(item);
            return true;
        }
        return false;
    }
    
    /**
     * Drop an item for the player's invetory into the room
     * @param itemName The item to remove
     * @return A message if it was succesfully removed or if it was not removed
     */
    public String dropItem(String itemName){
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getName().equalsIgnoreCase(itemName)){
                Item item = inventory.remove(i);
                currentRoom.addItem(item);
                return "You dropped the " + itemName + ".";
            }
        }
        return "You don't have a " + itemName + "in your inventory.";
    }
    
    /**
     * Check if the player is carrying a certain item
     * @ param itemname The name of the item to look for
     * @ return true if the item was found, otherwise false
     */
    public boolean hasItem(String itemName){
        for (Item item : inventory){
            if (item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Remove an item from the player's invetory
     * @param itemName The name of the itme to remove
     * @return true if the itme was removed, otherwise false
     */
    public boolean removeItem(String itemName){
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).getName().equalsIgnoreCase(itemName)){
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return a string with all of the items in the player's inventory
     * @return a string of the player's inventory
     */
    public String getInventoryString(){
        if(inventory.isEmpty()){
            return "You have nothing in your invetory";
        }
        String result = "You are carrying:\n";
        for (Item item : inventory){
            result += "" + item.toString() + "\n";
        }
        result += "Total weight: " + getCurrentCarryWeight() + "/" + maxWeight + " lbs";
        return result;
    }
    
    /**
     * Return the maximum wieght the player can carry
     * @return The maximum carry weight
     */
    public int getMaxCarryWeight(){
        return maxWeight;
    }
    
    /**
     * Increase the maximum weight the player can carry
     * @param amount The amount to increase the limit by
     */
    public void increaseMaxCarryWeight(int amount){
        maxWeight += amount;
    }
}