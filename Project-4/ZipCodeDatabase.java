import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
/****************************************************
 * Class that contains all methods to use for ZipCode
 ****************************************************/
public class ZipCodeDatabase{

    /** Global arrayList variable */
    ArrayList<ZipCode> zipArray;

    /****************************************************
    Constructor instantiates arrayList
     ****************************************************/
    public ZipCodeDatabase(){
        zipArray = new ArrayList<ZipCode>();
    }

    public void readZipCodeData(String filename){
        Scanner inFS = null; 
        FileInputStream fileByteStream = null;
        try{

            // open the File and set delimiters
            fileByteStream = new FileInputStream(filename);
            inFS = new Scanner(fileByteStream);
            inFS.useDelimiter("[,\r\n]+");

            // continue while there is more data to read
            while(inFS.hasNext()) {

                // read five data elements
                int zip = inFS.nextInt();
                String city = inFS.next();
                String state = inFS.next();
                double lat = inFS.nextDouble();
                double lon = inFS.nextDouble();

                // instantiate a new Student and add to ArrayList
                ZipCode z = new ZipCode(zip, city, state, lat, lon);
                zipArray.add(z);
                // this IS two lines of code
            }
            fileByteStream.close();

            // Could not find file
        }catch(FileNotFoundException error1) {
            System.out.println("Failed to read the data file: " + filename);

            // error while reading the file                      
        }catch(IOException error2) {
            System.out.println("Oops! Error related to: " + filename);
        }        

    }

    /****************************************************
    Method that searches zipArray for a matching zipcode
     ****************************************************/
    public ZipCode findZipCode(int zip){
        for(ZipCode z : zipArray){
            if(zip == z.getZip()){
                return z;
            }
        }
        return null;
    }

    /****************************************************
    calculates distance between two zip codes

    @param: two user-defined zip codes
    @return: Integer equal to distance in miles
     ****************************************************/
    public int distance(int zip1, int zip2){
        int earthRadius = 3959;

        //create two ZipCode objects
        ZipCode zipA = new ZipCode(zip1);
        ZipCode zipB = new ZipCode(zip2);

        //performs calculations if both zipcodes are valid
        if(findZipCode(zip1) != null || findZipCode(zip2) != null){
            zipA = findZipCode(zip1);
            zipB = findZipCode(zip2);
            double lat1 = Math.toRadians(zipA.getLat());
            double lat2 = Math.toRadians(zipB.getLat());
            double long1 = Math.toRadians(zipA.getLon());
            double long2 = Math.toRadians(zipB.getLon());
            
            //calculate
            double p1 = (Math.cos(lat1) * Math.cos(long1) * Math.cos(lat2) * Math.cos(long2));
            double p2 = (Math.cos(lat1) * Math.sin(long1) * Math.cos(lat2) * Math.sin(long2));
            double p3 = (Math.sin(lat1) * Math.sin(lat2));
            double distance = Math.acos(p1+p2+p3) * earthRadius;

            return (int)distance;
        }

        return -1;
    }

    /****************************************************
    uses distance method to determine what zipcodes are 
    within a certain radius

    @param: pZip - user-defined zipcode
    radius - maximum distance neighboring zip code can be 

     ****************************************************/
    public ArrayList<ZipCode> withinRadius(int pZip, int radius){
        ZipCode compare = findZipCode(pZip);
        ArrayList<ZipCode> withinR = new ArrayList<ZipCode>();
        
        //searches for zipcodes using the distance method
        for(ZipCode z : zipArray){
            int dist = distance(pZip, z.getZip());
            if(dist <= radius && dist > 0){
                withinR.add(z);
            }
        }
        return withinR;
    }

    /******************************************
     * Method finds zipcode farthest from
     * user entered
     * @param: zip code to search from
     ******************************************/
    public ZipCode findFurthest(int pZip){
        int max = 0;
        ZipCode initial = findZipCode(pZip);
        ZipCode furthest = new ZipCode(00000);
        
        //looks for zipcode if valid
        if(findZipCode(pZip) != null){
            for(ZipCode z : zipArray){
                int dist = distance(initial.getZip(), z.getZip());
                if(dist > max){
                    furthest = z;
                    max = dist;
                }
            }
        }
        return furthest;
    }

    /**************************************************
     * Searches for cities that contain same characters
     * as user entered string
     * 
     *@param str user entered string 
     *************************************************/
    public ArrayList<ZipCode> search(String str){
        str = str.toUpperCase();
        ArrayList<ZipCode> results = new ArrayList<ZipCode>();

        //adds matches to new array
        for(ZipCode z : zipArray){
            if(z.getCity().contains(str)){
                results.add(z);
            }
        }
        return results;
    }
    
    /***************************************************
     * Returns number of items in array
     ***************************************************/
    public int getCount(){
        if(zipArray.size() > 0){
            
            //return if nothing in array
            return -1;
        }
        return zipArray.size();
    }
}
