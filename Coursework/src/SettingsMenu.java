import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu extends JFrame {
    private JPanel Settings;
    private final ImageIcon arrowUp;
    private final ImageIcon arrowDown;
    private final ImageIcon arrowUpGray;
    private final ImageIcon arrowDownGray;
    private JButton difficultyUp;
    private JButton mapUp;
    private JButton gameModeUp;
    private JButton gameModeDown;
    private JButton mapDown;
    private JButton difficultyDown;
    private JTextArea difficultyTextArea;
    private JTextArea mapTextArea;
    private JTextArea gameModeTextArea;
    private JButton backButton;
    private final Game game;

    public SettingsMenu(Game input) {
        super();
        game=input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Settings);
        this.pack();
        Settings.setBackground(Color.BLUE);
        arrowUp = new ImageIcon("./ArrowUp.png");
        arrowDown = new ImageIcon("./ArrowDown.png");
        arrowUpGray = new ImageIcon("./ArrowUpGray.png");
        arrowDownGray = new ImageIcon("./ArrowDownGray.png");
        difficultyTextArea.setEditable(false);
        mapTextArea.setEditable(false);
        gameModeTextArea.setEditable(false);
        difficultyUp.addActionListener(e -> {
             game.increaseDifficulty();
             update();
        });
        difficultyDown.addActionListener(e -> {
            game.decreaseDifficulty();
            update();
        });
        mapUp.addActionListener(e -> {
            game.increaseMap();
            update();
        });
        mapDown.addActionListener(e -> {
            game.decreaseMap();
            update();
        });
        gameModeUp.addActionListener(e -> {
            game.increaseMode();
            update();
        });
        gameModeDown.addActionListener(e -> {
            game.decreaseMode();
            update();
        });
        backButton.addActionListener(e -> game.setScreen(0));
        update();
    }
    public void update(){
        difficultyTextArea.setText("Difficulty: " + game.getDifficulty());
        String mapText=switch (game.getMap()){
            case 0: yield "Forest";
            case 1: yield "Desert";
            case 2: yield "City";
            default: yield "";
        };
        mapTextArea.setText("Map: " + mapText);
        String modeText = switch(game.getMode()){
            case 0: yield "Survival";
            case 1: yield "Sandbox";
            case 2: yield "Tutorial";
            default: yield "";
        };
        gameModeTextArea.setText("Game Mode: " + modeText);
        difficultyUp.setIcon(arrowUp);
        difficultyDown.setIcon(arrowDown);
        mapUp.setIcon(arrowUp);
        mapDown.setIcon(arrowDown);
        gameModeUp.setIcon(arrowUp);
        gameModeDown.setIcon(arrowDown);
        if(game.getDifficulty()==9){
            difficultyUp.setIcon(arrowUpGray);
        }
        if(game.getDifficulty()==0){
            difficultyDown.setIcon(arrowDownGray);
        }
        if(game.getMode()==2){
            gameModeUp.setIcon(arrowUpGray);
        }
        if(game.getMode()==0){
            gameModeDown.setIcon(arrowDownGray);
        }
        if(game.getMap()==2){
            mapUp.setIcon(arrowUpGray);
        }
        if(game.getMap()==0){
            mapDown.setIcon(arrowDownGray);
        }

    }
}
