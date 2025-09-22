import javax.swing.*;

public class SaveMenu extends JFrame {
    private JPanel savePanel;
    private JLabel saveLabel;
    private Game game;

    public SaveMenu(Game input) {
        super();
        game=input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(savePanel);
        this.pack();
    }
}
