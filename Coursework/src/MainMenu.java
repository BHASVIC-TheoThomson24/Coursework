import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {

    private JPanel mainPanel;
    private JButton settingsButton;
    private JButton startGameButton;
    private JButton saveMenuButton;
    private JPanel imagePanel;
    private BufferedImage image;
    private Game game;

    public MainMenu(Game input){
        super();
        game=input;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        mainPanel.setBackground(Color.lightGray);
        try {
            image = ImageIO.read(new File("./Image.jpg"));
        }
        catch (IOException ignored) {
        }
        imagePanel.setLayout(new FlowLayout());
        imagePanel.setBackground(Color.lightGray);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imagePanel.add(imageLabel);

        settingsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.setScreen(1);

            }
        }
        );

        startGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.setScreen(2);

            }
        }
        );

        saveMenuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.setScreen(3);
            }
        }
        );
    }



}
