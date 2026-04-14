
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * Returns the name of the item
     * @return the item's name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the description of the item
     * @return the item's description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Returns the weight of the item
     * @return the 
     */
    public int getWeight(){
        return weight;
    }
}