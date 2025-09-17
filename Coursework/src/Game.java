import javax.swing.*;

public class Game {
    public Game(){
    }

    JFrame mainMenu=new MainMenu();
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
        switch(screen){
            case 0: mainMenu.setVisible(true);
            break;
            //Add cases for each screen
        }
    }
}
