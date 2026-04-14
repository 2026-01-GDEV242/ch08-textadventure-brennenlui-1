import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room startRoom;
    private Player player;
    private Stack<Room> roomHistory;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        startRoom = createRooms();
        parser = new Parser();
        player = new Player(startRoom, 20);
        roomHistory = new Stack<>();
    }

    public static void main(String[] args){
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room outside, theater, pub, lab, office, library, cafeteria, gym;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in the university library");
        cafeteria = new Room("in the campus cafeteria");
        gym = new Room("in the campus gym");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", library);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("south", cafeteria);
 
        lab.setExit("north", outside);
        lab.setExit("east", office);
 
        office.setExit("west", lab);
 
        library.setExit("south", outside);
        library.setExit("east", gym);
 
        cafeteria.setExit("north", pub);
 
        gym.setExit("west", library);

        outside.addItem(new Item("map", "a map of the campus", 1));
        theater.addItem(new Item("playbill", "a playbill of the show", 2));
        pub.addItem(new Item("mug", "a heavy ceramic beer mug", 5));
        lab.addItem(new Item("laptop", "a old research laptop", 10));
        lab.addItem(new Item("flask", "a flask used for experiments", 3));
        office.addItem(new Item("pen", "a black bic pen", 1));
        office.addItem(new Item("stapler", "a red office stapler", 2));
        library.addItem(new Item("book", "a giant textbook", 15));
        cafeteria.addItem(new Item("tray", "a plastic cafeteria tray", 2));
        cafeteria.addItem(new Item("cookie", "a mysterious magic cookie", 1));
        gym.addItem(new Item("bottle", "a reusable water bottle", 1));
        gym.addItem(new Item("weight", "a small dumbbell", 5));
        
        return outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK:
                look();
                break;
 
            case BACK:
                goBack();
                break;
 
            case TAKE:
                takeItem(command);
                break;
 
            case DROP:
                dropItem(command);
                break;
 
            case ITEMS:
                printInventory();
                break;
 
            case EAT:
                eatItem(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomHistory.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    /**
     * Prints a description of the current room
     */
    private void look(){
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    /**
     * Go back to the previous room
     */
    private void goBack(){
        if (roomHistory.isEmpty()) {
            System.out.println("You can't go back any further!");
        }
        else {
            Room previousRoom = roomHistory.pop();
            player.setCurrentRoom(previousRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }    
    }
    
    /**
     * Take an item from the current room and add it
     */
    private void takeItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
 
        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
 
        if (!currentRoom.hasItem(itemName)) {
            System.out.println("There is no " + itemName + " here.");
            return;
        }
 
        Item item = currentRoom.removeItem(itemName);
        if (player.pickUpItem(item)) {
            System.out.println("You picked up the " + itemName + ".");
        }
        else {
            currentRoom.addItem(item);
            System.out.println("The " + itemName + " is too heavy! You can't carry any more.");
        }
    }
    
    /**
     * Drop an item from the player's inventory
     * @param command The drop command with the item as the second word
     */
     private void dropItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
 
        String itemName = command.getSecondWord();
        System.out.println(player.dropItem(itemName));
    }
    
    /**
     * Print the player's current inventory
     */
    private void printInventory()
    {
        System.out.println(player.getInventoryString());
    }
    
    /**
     * Only lets you eat a cookie item. If the cookie is eaten then increase the max weight a play can carry
     * @param The eat command and the item as the second word
     */
    private void eatItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }
 
        String itemName = command.getSecondWord();
 
        if (!player.hasItem(itemName)) {
            System.out.println("You don't have a " + itemName + ".");
            return;
        }
 
        if (itemName.equalsIgnoreCase("cookie")) {
            player.removeItem(itemName);
            player.increaseMaxCarryWeight(20);
            System.out.println("You ate the magic cookie. You gained strength!");
            System.out.println("Your max carry weight is now " + player.getMaxCarryWeight() + " lbs.");
        }
        else {
            System.out.println("You can't eat the " + itemName + ".");
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
