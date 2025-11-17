import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameplayGrid extends JPanel{
    private ArrayList<JPanel> tiles = new ArrayList<>();
    private final GameMenu menu;
    //Make 10x10 grid of tiles
    private int height=10;
    private int width=10;
    private int cornerX=0;
    private int cornerY=0;
    public boolean expanding=false;
    public GameplayGrid(GameMenu menu) {
        setVisible(true);
        this.menu = menu;
        setLayout(new GridLayout(10,10));
        for(int i=0;i<100;i++){
            JPanel panel = new JPanel();
            panel.add(new EmptyTile());
            tiles.add(panel);
        }
        for(JPanel p : tiles){
            p.setLayout(new BorderLayout());
            p.setSize(50,50);
           add(p);
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
        else {
            //If they move past right edge of grid, add a new column
            boolean maxColumns = x >= 20;
            boolean maxRows = y >= 20;
            if (x > width - 1) {
                expanding = true;
                ++width;
                //Add a column
                if (!maxColumns) {
                    layout.setColumns(++columns);
                }

                //Faster to recreate array than to insert new tiles in middle
                ArrayList<JPanel> newTiles = new ArrayList<>(width * height);
                for(int i=0;i<width*height;i++){
                    newTiles.add(null);
                }
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width - 1; j++) {
                        newTiles.set(i*width+j,tiles.get(i * (width - 1) + j));
                    }
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    p.setSize(50, 50);
                    Random rand = new Random();
                    int value = rand.nextInt(100);
                    JComponent random;
                    if (value <= 84) {
                        random = new EmptyTile();
                    } else if (value <= 94) {
                        random = new Food();
                    } else {
                        random = new Ant(menu, x, i - 1);
                        menu.addAnt((Ant) random);
                    }
                    p.add(random);
                    newTiles.set(i*width+width-1, p);

                }

                tiles = newTiles;
                setCorner(getCornerX(),getCornerY());
                expanding = false;
            }

            if(y > height-1){
                expanding=true;
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
                        random=new EmptyTile();
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

                    if(!maxRows && i>=cornerX+1 && i<=cornerX+columns){
                        add(p);
                    }
                }
                expanding=false;
            }

            int index = width * y + x;
            if(index<tiles.size()){
                //Clear tile to create space
                tiles.get(index).removeAll();
                //Add new tile
                tiles.get(index).add(tile,BorderLayout.CENTER);
            }

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
                JPanel panel=tiles.get(width*(y+i)+(x+j));
                if(panel.getComponentCount()==0){
                    panel.add(new EmptyTile());
                }
                add(panel);
            }
        }
    }
    public JPanel getTile(int x, int y){
        if(x<0){
            return null;
        }
        if(y<0){
            return null;
        }
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
    //50 seconds average per tile on lowest difficulty to 500 seconds on highest difficulty.
    public void addRandomFood() {
        Random rand = new Random();
        int difficulty=Main.game.getDifficulty();
        float floatNumFood= (float) (width * height * (10 - difficulty)) /250000;
        int numFood= (int) (floatNumFood);
        // if numFood is 2.04, there will be a 96% chance for 2 food, and 4% chance for 3 food to attempt spawning
        if(floatNumFood-numFood > Math.random()){
            numFood++;
        }
        //For each food to be added, get a random tile and check if it is available to be changed


        for(int i=0;i< numFood;i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            for(int j=0;j<10;j++){
                int dx= rand.nextInt(7)-3;
                int dy= rand.nextInt(7)-3;
                JPanel panel = getTile(x+dx, y+dy);
                if(panel!=null){
                    Component tile=null;
                    if(panel.getComponentCount()>0){
                        tile = panel.getComponent(0);
                    }
                    else{
                        panel.add(new EmptyTile());
                    }
                    if (!(tile instanceof Ant || tile instanceof Pheromone || tile instanceof Food)) {
                        setTile(x+dx, y+dy, new Food());
                    }
                }

            }

        }

    }
    public int maxX(){
        return width-1;
    }
    public int maxY(){
        return height-1;
    }
    //Used for testing
    public void checkMissing(){
        Iterator<JPanel> it = tiles.iterator();
        int missing=0;
        while(it.hasNext()){
            try{
                it.next().getComponent(0);
            }catch (IndexOutOfBoundsException e){
                missing++;
            }
        }
        if(missing!=0){
            System.out.println("Missing: "+missing);
        }
    }
    //Only clears missing tiles in the visible area
    public void removeMissing(){
        for (Component component : getComponents()) {
            JPanel panel = (JPanel) component;
            if(panel.getComponentCount()==0){
                panel.add(new EmptyTile());
            }
        }
    }
    public int tileSize(){
        return tiles.size();
    }

}
