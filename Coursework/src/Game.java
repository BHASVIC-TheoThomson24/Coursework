import javax.swing.*;
import java.util.ArrayList;

public class Game {
    private MainMenu mainMenu;
    private SettingsMenu settingsMenu;
    private GameMenu gameMenu;
    private SaveMenu saveMenu;
    ArrayList<JFrame> frames=new ArrayList<>();
    public Game(){
        mainMenu=new MainMenu(this);
        frames.add(mainMenu);
        settingsMenu= new SettingsMenu(this);
        frames.add(settingsMenu);
        gameMenu= new GameMenu(this);
        frames.add(gameMenu);
        saveMenu= new SaveMenu(this);
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
        //Finds the selected screen in the arraylist and sets it to visible and gives it keyboard/input focus
        frames.get(screen).setVisible(true);
        frames.get(screen).transferFocus();
    }

    //difficulty from 0-9
    private int difficulty;
    // 0=Survival, 1=sandbox, 2=tutorial
    private int gameMode;
    //0=Forest, 1=Desert, 2=City
    private int map;
    public void increaseDifficulty(){
        if(difficulty<9) {
            difficulty++;
        }
    }
    public void decreaseDifficulty(){
        if(difficulty>0){
            difficulty--;
        }
    }
    public void increaseMap(){
        if(map<2){
            map++;
        }
    }
    public void decreaseMap(){
        if(map>0){
            map--;
        }
    }
    public void increaseMode(){
        if(gameMode<2){
            gameMode++;
        }
    }
    public void decreaseMode(){
        if(gameMode>0){
            gameMode--;
        }
    }
    public int getDifficulty(){
        return difficulty;
    }
    public int getMap(){
        return map;
    }
    public int getMode(){
        return gameMode;
    }
    public ArrayList<JFrame> getFrames(){
        return frames;
    }
}
