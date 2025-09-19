import javax.swing.*;
import java.util.ArrayList;

public class Game {
    JFrame mainMenu;
    JFrame settingsMenu;
    JFrame gameMenu;
    JFrame saveMenu;
    ArrayList<JFrame> frames=new ArrayList<>();
    public Game(){
        mainMenu=new MainMenu();
        frames.add(mainMenu);
        settingsMenu= new SettingsMenu();
        frames.add(settingsMenu);
        gameMenu= new GameMenu();
        frames.add(gameMenu);
        saveMenu= new SaveMenu();
        frames.add(saveMenu);
    }

    //screen that user is currently on: Menu (0), Settings (1), Game (2) or Save menu (3)
    int screen=0;

    public void start(){
        mainMenu.setVisible(true);
    }

    public void setScreen(int value){
        if(value<0 || value>3){
            System.out.println("Invalid screen value");
        }
        else{
            screen=value;
        }
        displayScreen();
    }


    public void displayScreen(){
        //Makes all screens invisible
        for(JFrame frame:frames){
            frame.setVisible(false);
        }
        //Finds the selected screen in the arraylist and sets it to visible
        frames.get(screen).setVisible(true);
    }
}
