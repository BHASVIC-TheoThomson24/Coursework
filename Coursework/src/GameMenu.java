import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GameMenu extends JFrame {
    private JPanel gamePanel;
    private Game game;
    private JPanel pauseMenu;
    private JTextArea pauseText;
    private ArrayList<Ant> ants = new ArrayList<Ant>();
    private JPanel gamePlay;
    private GameplayGrid grid= new GameplayGrid(this);
    private int food = 0;
    private Boolean controlDown = false;
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
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setScreen(0);
            }
        });
        pauseMenu.setLayout(new FlowLayout());
        pauseMenu.add(back);
        pauseMenu.setBackground(new Color(150,75,0));
        gamePanel.setBackground(new Color(150,75,0));
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
                    }
                }
            }
        });

        Ant ant1=new Ant();
        Ant ant2=new Ant();
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
    public Component getTile(int x, int y){
        try {
            return grid.getComponent(10 * y + x).getComponentAt(0, 0);
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public void addFood(){
        food++;
    }
    public void addRandomFood(){
        Random rand = new Random();
        int x= rand.nextInt(10);
        int y= rand.nextInt(10);
        if(! (getTile(x,y) instanceof Ant  || getTile(x,y) instanceof Pheromone || getTile(x,y) instanceof Food)){
            setTile(x,y,new Food());
        }

    }

}
