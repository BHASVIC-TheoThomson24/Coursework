import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    private JPanel mainPanel;
    private JButton settingsButton;
    private JButton startGameButton;
    private JButton saveMenuButton;

    public MainMenu(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        settingsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Main.game.setScreen(1);

            }
        }
        );

        startGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Main.game.setScreen(2);

            }
        }
        );

        saveMenuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Main.game.setScreen(3);
            }
        }
        );
    }



}
