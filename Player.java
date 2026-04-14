import java.util.ArrayList;
/**
 * Write a description of class Player here.
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

    
}