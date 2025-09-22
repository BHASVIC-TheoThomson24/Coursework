import javax.swing.*;

public class GameMenu extends JFrame {
    private JLabel gameLabel;
    private JPanel gamePanel;
    private Game game;
    public GameMenu(Game input) {
        super();
        game = input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();
    }

}
