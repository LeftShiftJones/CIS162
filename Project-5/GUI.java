import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.*;
/*************************************************************
 * GUI for a Zip Code Database
 *
 * @author Scott Grissom
 * @version October 7, 2015
 ************************************************************/
public class GUI extends JFrame implements ActionListener{

    /** the analyzer that doe all the real work */
    Game game;

    /** Buttons */
    JButton north, south, east, west, eat, drink, craft,
    help, drop, pickup, look, list;

    /** Text */
    JTextArea results;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem newGameItem;

    /*****************************************************************
     * Main Method
     ****************************************************************/
    public static void main(String args[]){
        GUI gui = new GUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Ryan's Adventure Game");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/
    public GUI(){

        // instantiate the analyzer and read the data file
        game = new Game();
        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        // create results area to span one column and 11 rows
        results = new JTextArea(20,40);
        JScrollPane scrollPane = new JScrollPane(results);
        DefaultCaret caret = (DefaultCaret)results.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        results.setText(""+game.getMessage());
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 7;
        loc.insets.left = 20;
        loc.insets.right = 20;
        loc.insets.bottom = 20;
        add(scrollPane, loc);
        loc = new GridBagConstraints();

        // create Results label
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        add(new JLabel("Text:"), loc);

        // create Choices label
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Choices"), loc);
        loc = new GridBagConstraints();

        north = new JButton("North");
        south = new JButton("South");
        east = new JButton("East");
        west = new JButton("West");
        pickup = new JButton("Pickup");
        look = new JButton("Look");
        drink = new JButton("Drink");
        eat = new JButton("Eat");
        craft = new JButton("Craft");
        list = new JButton("List");
        help = new JButton("Help");
        drop = new JButton("Drop");

        loc.insets.right = 20;
        loc.insets.bottom = 20;
        loc.gridy = 1;
        loc.gridx = 3;
        add(north, loc);
        loc.gridy = 3;
        add(south, loc);
        loc.gridx = 2;
        loc.gridy = 2;
        add(west, loc);
        loc.gridx = 4;
        add(east, loc);
        loc.gridy = 7;
        loc.gridx = 2;
        add(eat, loc);
        loc.gridx = 3;
        add(drink, loc);
        loc.gridx = 4;
        add(look, loc);
        loc.gridx = 5;
        add(help, loc);
        loc.gridy = 8;
        loc.gridx = 2;
        add(craft, loc);
        loc.gridx = 3;
        add(pickup, loc);
        loc.gridx = 4;
        add(drop, loc);
        loc.gridx = 5;
        add(list, loc);

        north.addActionListener(this);
        south.addActionListener(this);
        east.addActionListener(this);
        west.addActionListener(this);
        look.addActionListener(this);
        list.addActionListener(this);
        help.addActionListener(this);
        drop.addActionListener(this);
        craft.addActionListener(this);
        eat.addActionListener(this);
        drink.addActionListener(this);
        pickup.addActionListener(this);

        // set up File menu
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        newGameItem = new JMenuItem("New Game");
        quitItem.addActionListener(this);
        newGameItem.addActionListener(this);
        fileMenu.add(newGameItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        // FIX ME: set the actioni listener for the menu items

    }
    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     *
     * @param e Event Detected
     ****************************************************************/
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent event = (JComponent) e.getSource();
        if(event == north){
            game.move("north");
        }
        if(event == south){
            game.move("south");
        }
        if(event == west){
            game.move("west");
        }
        if(event == east){
            game.move("east");
        }
        if(event == pickup){
            game.pickup();
        }
        if(event == drop){
            String question = "What Item Would You Like To Drop?";
            String str = JOptionPane.showInputDialog(question);
            game.drop(str);
        }
        if(event == help){
            game.help();
        }
        if(event == list){
            game.list();
        }
        if(event == eat){
            String question = "What Item Would You Like To Eat?";
            String str = JOptionPane.showInputDialog(question);
            game.eat(str);
        }
        if(event == drink){
            String question = "What Item Would You Like To Drink?";
            String str = JOptionPane.showInputDialog(question);
            game.drink(str);
        }
        if(event == craft){
            game.craft();
        }
        if(event == look){
            game.look();
        }
        if(event == newGameItem){
            restart();
        }
        if(event == quitItem){
            System.exit(1);
        }
        results.append(game.getMessage());
    }

    private void gameOver(){
        north.setEnabled(false);
        south.setEnabled(false);
        east.setEnabled(false);
        west.setEnabled(false);
        drop.setEnabled(false);
        eat.setEnabled(false);
        drink.setEnabled(false);
        look.setEnabled(false);
        list.setEnabled(false);
        pickup.setEnabled(false);
        craft.setEnabled(false);
        help.setEnabled(false);
    }

    private void restart(){
        north.setEnabled(true);
        south.setEnabled(true);
        east.setEnabled(true);
        west.setEnabled(true);
        drop.setEnabled(true);
        eat.setEnabled(true);
        drink.setEnabled(true);
        look.setEnabled(true);
        list.setEnabled(true);
        pickup.setEnabled(true);
        craft.setEnabled(true);
        help.setEnabled(true);
        game = new Game();
        results.setText("");
        results.append(game.getMessage());
        game.look();
        results.append(game.getMessage());
    }
}
