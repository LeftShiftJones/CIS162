/***********************************************************************
 * Basic Item class to be used by Room Class
 *
 * @author [Ryan A. Jones]
 * @version [1.0, 12/08/2015]
 ***********************************************************************/
public class Item
{

    /** Name and Description */
    private String name, description;

    /** Weight */
    private int weight;

    /** Edible, drinkable */
    private boolean edible, drinkable;

    /******************************************************************
     * Initializes variables
    @param n Name of Item
    @param d Description of Item
    @param w Weight of Item
    @param eat Boolean for edible
    @param drink Boolean for drinkable
     ******************************************************************/
    public Item(String n, String d, int w, boolean eat, boolean drink){
        name = n;
        description = d;
        weight = w;
        edible = eat;
        drinkable = drink;
    }

    /******************************************************************
     * @return boolean edible
     ******************************************************************/
    public boolean isEdible(){
        return edible;
    }

    /******************************************************************
     * @return boolean drinkable
     ******************************************************************/
    public boolean isDrinkable(){
        return drinkable;
    }

    /******************************************************************
     * @return String Name
     ******************************************************************/
    public String getName(){
        return name;
    }

    /******************************************************************
     * @return String Description
     ******************************************************************/
    public String getDescription(){
        return description;
    }

    /******************************************************************
     * @return Int Weight
     ******************************************************************/
    public int getWeight(){
        return weight;
    }
}
