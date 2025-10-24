import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameMenu extends JFrame {
    private JPanel gamePanel;
    private final Game game;
    private JPanel pauseMenu;
    private JTextArea pauseText;
    private final ArrayList<Ant> ants = new ArrayList<>();
    private final ArrayList<Ant> newAnts= new ArrayList<>();
    private JPanel gamePlay;
    private JTextArea stats;
    private final GameplayGrid grid= new GameplayGrid(this);
    private int food = 0;
    private Boolean controlDown = false;
    //Make camera follow mainAnt
    private Ant mainAnt;
    private JPanel statsPanel;
    public GameMenu(Game input) {
        super();
        game = input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();

        gamePlay.setLayout(new GridLayout(1,1));
        gamePlay.add(grid);
        setAlwaysOnTop(true);
        pauseText.setEditable(false);
        pauseText.setFocusable(false);
        JButton back = new JButton("Back");
        back.addActionListener(e -> game.setScreen(0));
        pauseMenu.setLayout(new FlowLayout());
        pauseMenu.add(back);
        pauseMenu.setBackground(new Color(150,75,0));
        gamePanel.setBackground(new Color(150,75,0));
        stats.setBackground(new Color(150,75,0));
        stats.setText("Food: 0");
        gamePanel.setFocusable(true);
        gamePanel.setLayout(new FlowLayout());
        gamePanel.add(pauseMenu);
        gamePanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    boolean flag = pauseText.isVisible();
                    //If pause menu is visible it will hide, and visa versa
                   pauseText.setVisible(!flag);
                   for (Component c : pauseMenu.getComponents()) {
                       c.setVisible(!flag);
                   }
                }
                else{
                    int direction=-1;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_CONTROL: controlDown=!controlDown;
                        break;
                        case KeyEvent.VK_W: direction=0;
                        break;
                        case KeyEvent.VK_D: direction=1;
                        break;
                        case KeyEvent.VK_S: direction=2;
                        break;
                        case KeyEvent.VK_A: direction=3;
                        break;


                        default:
                    }
                    if(direction!=-1) {
                        for (Ant ant : ants) {
                            if (ant.getPlaying()) {
                                ant.move(direction);
                            }
                        }
                        if(mainAnt != null){
                            mainAnt.moveCamera();
                        }
                        updateAnts();
                    }
                }
            }
        });

        Ant ant1=new Ant(this, 0 ,5);
        Ant ant2=new Ant(this, 1 ,5);
        ants.add(ant1);
        ants.add(ant2);
        setTile(0,5,ant1);
        setTile(1,5,ant2);
        setTile(2,2,new Food());
        setTile(1,4,new Pheromone(0,2));
        setTile(1,3,new Pheromone(0,2));
        setTile(1,2,new Pheromone(2,1));
    }
    public void setTile(int x, int y, JComponent tile){
        grid.setTile(x,y,tile);
    }
    public void changeAnt(){
        if(!controlDown) {
            for (Ant ant : ants) {
                ant.setPlaying(false);
            }

        }
    }
    public JPanel getTile(int x, int y){
        try {
            return grid.getTile(x,y);
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public void addFood(){
        food++;
        updateStats();
    }


    public void updateStats(){
        stats.setText("Food: "+food);
    }
    public GameplayGrid getGrid(){
        return grid;
    }
    //Ants added in 2 stages to avoid concurrency error (new ants added while looping through ant arraylist)
    public void addAnt(Ant ant){
        newAnts.add(ant);
    }

    public void updateAnts(){
        ants.addAll(newAnts);
        newAnts.clear();
    }
    public void setMainAnt(Ant ant){
        mainAnt = ant;
    }
}
