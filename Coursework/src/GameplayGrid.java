import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameplayGrid extends JPanel{
    private ArrayList<JPanel> tiles = new ArrayList<JPanel>();
    private GameMenu menu;
    //Make 10x10 grid of tiles
    public GameplayGrid(GameMenu menu) {
        setVisible(true);
        this.menu = menu;
        setLayout(new GridLayout(10,10));
        for(int i=0;i<100;i++){
            tiles.add(new JPanel());
        }
        for(JPanel p : tiles){
            p.setLayout(new BorderLayout());
            p.setSize(50,50);
           add(p);
        }
        for(int i=0;i<100;i++){
            setTile(i/10,i%10, new JLabel(new ImageIcon("./EmptyTile.png")));
        }

        for (Component c : getComponents()) {
            c.setBackground(new Color(150,75,0));
        }

    }
    public void setTile(int x,int y, JComponent tile){
        //x and y begin at 0 in the top left corner
        if (x < 0 || y < 0 || x > 9 || y > 9) {
            System.out.println("Invalid movement");
            }
        else{
            int index = 10 * y + x;
            //Clear tile to create space
            tiles.get(index).removeAll();
            //Add new tile
            tiles.get(index).add(tile,BorderLayout.CENTER);
            if(tile instanceof Ant){
                ((Ant) tile).setCoordinates(x,y);
            }
        }


    }

}
