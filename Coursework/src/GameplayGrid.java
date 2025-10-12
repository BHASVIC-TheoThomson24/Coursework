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
        GridLayout layout = (GridLayout) getLayout();
        int columns= layout.getColumns();
        int rows= layout.getRows();
        if (x < 0 || y < 0) {
            System.out.println("Invalid movement");
        }
        else{
            //If they move past right edge of grid, add a new column
            if(x >columns-1){
                layout.setColumns(++columns);
                //Fill column with empty tiles
                for(int i=1;i<=rows;i++){
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    p.setSize(50,50);
                    p.add(new JLabel(new ImageIcon("./EmptyTile.png")));
                    add(p,columns*i-1);
                    tiles.add(columns*i-1,p);
                }
            }
            if(y >rows-1){
                //Add a row
                layout.setRows(++rows);
                //Fill row
                for(int i=1;i<=columns;i++){
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    p.setSize(50,50);
                    p.add(new JLabel(new ImageIcon("./EmptyTile.png")));
                    add(p);
                    tiles.add(p);

                }
            }

            int index = columns * y + x;
            //Clear tile to create space
            tiles.get(index).removeAll();
            //Add new tile
            tiles.get(index).add(tile,BorderLayout.CENTER);
            if(tile instanceof Ant){
                ((Ant) tile).setCoordinates(x,y);
            }

        }
        revalidate();
        repaint();
    }

}
