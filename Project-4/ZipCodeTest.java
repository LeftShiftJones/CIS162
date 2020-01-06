import java.util.ArrayList;
/**
 * Test class for ZipCodeDatabase functions
 */
public class ZipCodeTest{
    /***********************************************
     * Main method to run tests
     ***********************************************/
    public static void main(String args[]){
        System.out.println("Welcome!");
        ZipCodeDatabase db = new ZipCodeDatabase();
        db.readZipCodeData("zipcodes.txt");
        int testsPassed = 0;
        
        //test number of items in zipcodes.txt
        if(db.getCount() < 29000 && db.getCount() > 30000){
            System.out.println("Test Failed: zipcodes.txt should " +
                "only have 29,000 items, Found: " + db.getCount());
        } else{
            testsPassed++;
        }
        
        //test valid zipcode
        String str = db.findZipCode(16511).getCity();
        if(str.equals("ERIE")){
            //error
            System.out.println("Not a valid zipcode");
        } else{
            testsPassed++;
        }

        //test distance
        //Hudsonville, MI; Erie, PA (about 300 Miles)
        int dist = db.distance(49426, 16511) - 300;
        if(dist > 10 || dist < 1){
            System.out.println("Wrong distance value, method error");
        } else{
            testsPassed++;
        }
        
        //test search
        ArrayList<ZipCode> searchArray = new ArrayList<ZipCode>();
        searchArray = db.search("hudson");
        int test = searchArray.size();
        if(test != 25){
            System.out.println("Wrong number of cities retrieved, method error");
        } else{
            testsPassed++;
        }
        
        //test within Radius
        
        searchArray = db.withinRadius(49426, 10);
        test = searchArray.size();
        if(test != 7){
            System.out.println("Wrong number of cities retrieved, method error");
        } else{
            testsPassed++;
        }
        
        System.out.println("You passed " + testsPassed + " out of 5 tests");
    }
}
