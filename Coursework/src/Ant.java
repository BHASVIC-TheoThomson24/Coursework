import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ant extends JButton {
    public Ant(){
        setIcon(new ImageIcon("./Ant.png"));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // Give focus back to the gameMenu after being clicked
                Main.game.getFrames().get(2).transferFocus();
            }
        });
    }
}
