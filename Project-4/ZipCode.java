
/**
 * Write a description of class Proj4 here.
 * 
 * @author (Ryan Jones) 
 * @version (11/10/2015)
 */
public class ZipCode{
    /** int to store zipcode */
    private int  zipCode;

    /** strings for city and state */
    private String  city,  state;

    /** doubles for latitude and logitude */
    private double  lat,  lon;

    /***********************************
     * Constructor initializes variables
     ***********************************/
    public ZipCode(int pZip, String pCity, String pState,
    double pLat, double pLon){
        zipCode = pZip;
        city = pCity;
        state = pState;
        lat = pLat;
        lon = pLon;
    }
    
    public ZipCode(int pZip){
        zipCode = pZip;
        city = "UNKNOWN";
        state = "St";
        lat = 0.0;
        lon = 0.0;
    }
    
    public String toString(){
        String outputString = city + ", " + state + " " + zipCode + "\n";
        return outputString;
    }
    
    /***********************************
     * Methods that return different
     * values for each zipcode
     ***********************************/
    public int getZip(){
        return zipCode;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }

    /*********************************
     * Methods that set different
     * values
     *********************************/

    public void setZip(int setIt){
        zipCode = setIt;
    }

    public void setCity(String setIt){
        city = setIt;
    }

    public void setState(String setIt){
        state = setIt;
    }

    public void setLat(double setIt){
        lat = setIt;
    }

    public void setLon(double setIt){
        lon = setIt;
    }
}
