import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/*************************************************************
 * GUI for a Zip Code Database
 * 
 * @author Scott Grissom, Ryan Jones
 * @version October 7, 2015
 ************************************************************/
public class GUI extends JFrame implements ActionListener{

    /** the analyzer that does all the real work */
    ZipCodeDatabase database;

    /** buttons to perform different functions */
    JButton searchButton;
    JButton distanceButton;
    JButton findButton;
    JButton furthestButton;
    JButton radiusButton;
    //FIX ME: define five buttons

    /** Results */
    JTextArea results;

    /** text fields */
    JTextField zip1;
    JTextField zip2;
    JTextField radiusField;
    JTextField nameField;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem openItem;  

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        GUI gui = new GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Zip Codes");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public GUI(){

        // instantiate the analyzer  
        database = new ZipCodeDatabase();

        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        // create results area to span one column and 11 rows
        results = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(results);
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 11;  
        loc.insets.left = 20;
        loc.insets.right = 20;
        loc.insets.bottom = 20;
        add(scrollPane, loc);
        loc = new GridBagConstraints();
        
        // create Results label
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        add(new JLabel("Results"), loc);

        //set new constraints and set labels
        loc.anchor = GridBagConstraints.LINE_START;
        loc.gridx = 1;
        loc.gridy = 1;
        add(new JLabel("Zip1"), loc);
        
        loc.gridx = 1;
        loc.gridy = 2;
        add(new JLabel("Zip2"), loc);
        
        loc.gridx = 1;
        loc.gridy = 3;
        add(new JLabel("Radius"), loc);
        
        loc.gridx = 1;
        loc.gridy = 4;
        add(new JLabel("Name"), loc);
        
        // create Choices label
        loc.gridx = 2;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Choices"), loc);      

        
        //zip1 textfield
        zip1 = new JTextField(10);
        loc.gridy = 1;
        add(zip1, loc);
        loc.insets.bottom = 20;

        //zip2 textfield
        zip2 = new JTextField(10);
        loc.gridy = 2;
        add(zip2, loc);

        //radius textfield
        radiusField = new JTextField(5);
        loc.gridy = 3;
        add(radiusField, loc);

        //name textfield
        nameField = new JTextField(15);
        loc.gridy = 4;
        add(nameField, loc);

        //center items
        loc.anchor = GridBagConstraints.CENTER;
        
        // add find button
        findButton = new JButton("Find Zip1");
        loc.gridy = 5;
        loc.gridwidth = 2;
        add(findButton, loc);

        // add search button
        searchButton = new JButton("Search for zipcode with Name");
        loc.gridy = 6;
        add(searchButton, loc);

        // add distance button
        distanceButton = new JButton("Distance between Zip1 & Zip2");
        loc.gridy = 7;
        add(distanceButton, loc);

        //add farthes button
        furthestButton = new JButton("Furthest Zipcode from Zip1");
        loc.gridy = 8;
        add(furthestButton, loc);

        //add radius button
        radiusButton = new JButton("Zipcodes within Radius of Zip1");
        loc.gridy = 9;
        add(radiusButton, loc);

        //adds buttons
        findButton.addActionListener(this);
        searchButton.addActionListener(this);
        distanceButton.addActionListener(this);
        furthestButton.addActionListener(this);
        radiusButton.addActionListener(this);

        // set up File menu
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        openItem = new JMenuItem("Open File...");
        fileMenu.add(openItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        // set up File menu listeners
        quitItem.addActionListener(this);
        openItem.addActionListener(this);

    }

    /*****************************************************************
     * Search city and state for any match
     ****************************************************************/ 
    private void searchByName(){

        // retrieve the zip codes with the matching String
        ArrayList <ZipCode> list = database.search(nameField.getText());

        // dislay the results
        results.setText("Cities that contain '"+nameField.getText()+"'\n\n");
        for (ZipCode z : list){
            results.append(z + "\n");
        }
        results.append("\nTotal: " + list.size());
    }

    /*****************************************************************
     * Calculate distances between two zip codes if the textfields
     * contain valid integers
     ****************************************************************/ 
    private void calcDistance (){
        
        //set variables
        checkValidInteger(zip1.getText(), "Zip1");
        checkValidInteger(zip2.getText(), "Zip2");
        int start = Integer.parseInt(zip1.getText());
        int end = Integer.parseInt(zip2.getText());
        
        // calculate and display
        int dist = database.distance(start, end);
        
        //print text
        results.setText("The distance between " + start + 
            " and " + end + ":\n" + dist + " miles.");
    }

    /*****************************************************************
     * find a zip code
     ****************************************************************/ 
    private void findZipCode (){
        checkValidInteger(zip1.getText(), "Zip1");

        // search for the zip code
        int z1 = Integer.parseInt(zip1.getText());
        ZipCode z = database.findZipCode(z1);

        // if no zip code found
        if(z == null){
            results.setText("No city found with zip code " + z1);
        } else{
            results.setText(z.toString());
        }
    }

    /*****************************************************************
     * find a zip code farthest from
     ****************************************************************/ 
    private void findFurthest(){
        
        //get values
        checkValidInteger(zip1.getText(), "Zip1");
        int startZip = Integer.parseInt(zip1.getText());
        
        //new database for farthes zipcode
        ZipCode far = database.findFurthest(startZip);
        
        //print text
        results.setText("Farthest from " + startZip + " is: \n" + far.getCity() + ", " + far.getState());
    }

    /*****************************************************************
     * find zips within a specific radius
     ****************************************************************/ 
    private void findZipCodesWithinRadius (){
        
        //get values
        int zipOfRadius = Integer.parseInt(zip1.getText());
        int radius = Integer.parseInt(radiusField.getText());
        
        //make arrayList
        checkValidInteger(radiusField.getText(), "Radius");
        ArrayList<ZipCode> radiusArray = new ArrayList<ZipCode>();
        radiusArray = database.withinRadius(zipOfRadius, radius);
        
        //set text
        results.setText("Cities withing " + radius + " miles of " + zipOfRadius + ":\n");
        for(ZipCode z : radiusArray){
            results.append(z.getCity() + ", " + z.getState() + " " + z.getZip() + "\n");
        }
        
    }
    
    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was used
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent buttonPressed = (JComponent) e.getSource();

        //calculates distance
        if(buttonPressed == distanceButton){
            calcDistance();
        }

        //searches for zipcode from name
        if(buttonPressed == searchButton){
            searchByName();
        }

        //searches for furthest zipcode
        if(buttonPressed == furthestButton){
            findFurthest();
        }
        
        //searches for zipcode #
        if(buttonPressed == findButton){
            findZipCode();
        }
        
        //lists all zipcodes within the radius
        if(buttonPressed == radiusButton){
            findZipCodesWithinRadius();
        }
        
        //open file
        if(buttonPressed == openItem){
            openFile();
        }
        
        //quits applet
        if(buttonPressed == quitItem){
            System.exit(1);
        }
    }

    /*****************************************************************
     * open a data file with the name selected by the user
     ****************************************************************/ 
    private void openFile(){

        // create File Chooser so that it starts at the current directory
        String userDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(userDir);

        // show File Chooser and wait for user selection
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            database.readZipCodeData(filename);          
        }
    }

    /*****************************************************************
     * Check if the String contains a valid integer.  Display
     * an appropriate warning if it is not valid.
     * 
     * @param numStr - the String to be checked
     * @param label - the textfield name that contains the String
     * @return true if valid
     ****************************************************************/   
    private boolean checkValidInteger(String numStr, String label){
        boolean isValid = true;
        try{
            int val = Integer.parseInt(numStr);

            // display error message if not a valid integer    
        }catch(NumberFormatException e){
            isValid = false;
            JOptionPane.showMessageDialog(this, "Enter an integer in " + label);

        }    
        return isValid;
    }
}