import javax.swing.*;

public class EmptyTile extends JLabel {
    private static final ImageIcon icon = new ImageIcon("./EmptyTile.png");
    public EmptyTile() {
        super(icon);
    }
}
