import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameplayGrid extends JPanel{
    private ArrayList<JPanel> tiles = new ArrayList<>();
    private GameMenu menu;
    //Make 10x10 grid of tiles
    private int height=10;
    private int width=10;
    private int cornerX=0;
    private int cornerY=0;
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
            boolean maxColumns= x>=20;
            boolean maxRows= y>=20;

            if(x >width-1){
                ++width;
                //Add a column
                if(!maxColumns){
                    layout.setColumns(++columns);
                }

                //Fill column with empty tiles
                for(int i=1;i<=rows;i++){
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    p.setSize(50,50);
                    Random rand=new Random();
                    int value=rand.nextInt(100);
                    JComponent random;
                    if(value<=84){
                        random=new JLabel(new ImageIcon("./EmptyTile.png"));
                    }
                    else if(value<=94){
                        random=new Food();
                    }
                    else{
                        random=new Ant(menu,x,i-1);
                        menu.addAnt((Ant) random);
                    }
                    p.add(random);
                    tiles.add(width*i-1,p);
                    if(!maxColumns){
                        add(p,columns*i-1);
                    }
                }
                if(maxColumns){
                    setCorner(Math.max(0,x-19),Math.max(0,y-19));
                }
            }

            if(y >rows-1){
                ++height;
                //Add a row
                if(!maxRows){
                    layout.setRows(++rows);
                }
                //Fill row
                for(int i=1;i<=width;i++){
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    p.setSize(50,50);
                    Random rand=new Random();
                    int value=rand.nextInt(100);
                    JComponent random;
                    if(value<=84){
                        random=new JLabel(new ImageIcon("./EmptyTile.png"));
                    }
                    else if(value<=94){
                        random=new Food();
                    }
                    else{
                        random=new Ant(menu,i-1,y);
                        menu.addAnt((Ant) random);
                    }
                    p.add(random);
                    tiles.add(p);
                    if(!maxRows && i>getCornerX() && i<=getCornerX()+columns){
                        add(p);
                    }
                }
                if(maxRows){
                    setCorner(Math.max(0,x-19),Math.max(0,y-19));
                }
            }

            int index = width * y + x;
            //Clear tile to create space
            tiles.get(index).removeAll();
            //Add new tile
            tiles.get(index).add(tile,BorderLayout.CENTER);

        }
        revalidate();
        repaint();
    }
    //Sets top left corner of grid to co-ordinates, without changing its size
    public void setCorner(int x, int y){
        removeAll();
        cornerX=x;
        cornerY=y;
        GridLayout layout = (GridLayout) getLayout();
        int columns= layout.getColumns();
        int rows= layout.getRows();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                add(tiles.get(width*(y+i)+(x+j)));
            }
        }
    }
    public JPanel getTile(int x, int y){
        if(x>=width || y>=height){
            return null;
        }
        JPanel output;
        try{
            output=tiles.get(width*y+x);

        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
        return output;
    }
    public int getCornerX(){
        return cornerX;
    }
    public int getCornerY(){
        return cornerY;
    }

}
