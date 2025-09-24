import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameMenu extends JFrame {
    private JPanel gamePanel;
    private Game game;
    private JPanel pauseMenu;
    private JTextArea pauseText;
    private ImageIcon food=new ImageIcon("./Leaf.png");

    public GameMenu(Game input) {
        super();
        game = input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();

        setAlwaysOnTop(true);
        pauseText.setEditable(false);
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
        gamePanel.add(new Ant());
        gamePanel.add(new Food());

        for (Component c : gamePanel.getComponents()) {
            c.setBackground(new Color(150,75,0));
        }
    }

}
