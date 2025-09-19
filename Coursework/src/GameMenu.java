import javax.swing.*;

public class GameMenu extends JFrame {
    private JLabel gameLabel;
    private JPanel gamePanel;

    public GameMenu() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();
    }

}
