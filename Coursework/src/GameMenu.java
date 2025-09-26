import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameMenu extends JFrame {
    private JPanel gamePanel;
    private Game game;
    private JPanel pauseMenu;
    private JTextArea pauseText;
    private ArrayList<JPanel> tiles = new ArrayList<JPanel>();
    private JPanel gamePlay;

    public GameMenu(Game input) {
        super();
        game = input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();

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
            }
        });
        //Make 10x10 grid of tiles
        gamePlay.setLayout(new GridLayout(10,10));
        for(int i=0;i<100;i++){
            tiles.add(new JPanel());
        }
        for(JPanel p : tiles){
            p.setLayout(new BorderLayout());
            p.setSize(50,50);
            gamePlay.add(p);
        }
        for(int i=0;i<100;i++){
            setTile(i/10,i%10, new JLabel(new ImageIcon("./EmptyTile.png")));
        }

        for (Component c : gamePanel.getComponents()) {
            c.setBackground(new Color(150,75,0));
        }
        setTile(0,5,new Ant());
        setTile(1,5,new Ant());
        setTile(2,2,new Food());
        setTile(1,4,new Pheromone(0,2));
        setTile(1,3,new Pheromone(0,2));
        setTile(1,2,new Pheromone(2,1));



    }
    public void setTile(int x,int y, JComponent tile){
        //x and y begin at 0 in the top left corner
        tiles.get(10*y+x).add(tile,BorderLayout.CENTER);
    }

}
