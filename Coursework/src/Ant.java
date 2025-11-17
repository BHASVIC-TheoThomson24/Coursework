import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Ant extends JButton {
    private static final ImageIcon icon = new ImageIcon("./Ant.png");
    private int x;
    private int y;
    private final GameMenu menu;
    private final GameplayGrid grid;
    protected Boolean playing=false;
    protected Boolean hasFood=false;
    protected Boolean leaveTrail=false;
    private int directionMoved=-1;
    private int previousDirection=-1;
    public Ant(GameMenu menu, int x, int y){
        this.x=x;
        this.y=y;
        this.menu=menu;
        this.grid=menu.getGrid();
        setIcon(icon);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        //When ant is clicked

    }
    //0=up, 1=right, 2=down, 3=left
    public void move(int direction){
       int dx=0;
       int dy=0;
       //increasing y moves down to next row
        switch(direction){
            case 0: dy=-1;
            break;
            case 1: dx=1;
            break;
            case 2: dy=1;
            break;
            case 3: dx=-1;
            break;
            default:
        }

        JPanel panel = menu.getTile(x+dx,y+dy);
        Component tile=null;
        if(panel!=null){
            if(panel.getComponentCount()>0){
                tile=panel.getComponent(0);

            }
            else{
                panel.add(new EmptyTile());
            }
        }

        //Cannot move to a space occupied by another ant
        if(tile instanceof Ant){
            return;
        }
        if(tile instanceof Food){
            if(!hasFood){
                hasFood=true;
                menu.addFood();
            }
            else{
                return;
            }
        }

        if(x+dx>=0 && y+dy>=0){
            //If it is first time moving, set both directions to be the same.
            if(directionMoved==-1){
                directionMoved=direction;
                previousDirection=direction;
            }
            //Otherwise previousDirection is the direction from the last method call, direction is the value called with currently
            previousDirection=directionMoved;
            directionMoved=direction;
            if(leaveTrail){
                try{
                    menu.setTile(x,y,new Pheromone(previousDirection+2,directionMoved));

                }catch(Error e){
                    menu.setTile(x,y,new EmptyTile());
                }
            }
            else{
                menu.setTile(x,y,new EmptyTile());
            }
            x=x+dx;
            y=y+dy;
            menu.setTile(x,y,this);
            if(Math.random()<0.01){
                eat();
            }


        }
    }
    public Boolean getPlaying(){
        return playing;
    }
    public void setPlaying(Boolean playing){
        this.playing = playing;
    }
    //Only used by the ant which the camera is following
    public void moveCamera(){
        if(x<grid.getCornerX()){
            grid.setCorner(x,grid.getCornerY());
        }
        if(y<grid.getCornerY()){
            grid.setCorner(grid.getCornerX(),y);
        }
        GridLayout layout = (GridLayout) grid.getLayout();
        int columns=layout.getColumns();
        int rows = layout.getRows();
        if(x>=grid.getCornerX()+columns){
            grid.setCorner(grid.getCornerX()+1,grid.getCornerY());
        }
        if(y>=grid.getCornerY()+rows){
            grid.setCorner(grid.getCornerX(),grid.getCornerY()+1);
        }
    }
    public void setMainAnt(){
        menu.setMainAnt(this);
    }
    public void setFood(Boolean b){
        hasFood=b;
    }
    public Boolean getFood(){
        return hasFood;
    }
    public void eat(){
        hasFood=false;
        menu.decreaseFood();
    }
    public void toggleTrail(){
        leaveTrail=!leaveTrail;
    }
    public void setTrail(boolean b){
        leaveTrail=b;
    }
    public void tick(){
        //Ant accesses 4 adjacent tiles
        boolean moved = false;
        ArrayList<JPanel> adjacentTiles = new ArrayList<>();
        //Order matches direction e.g. tile with index 0 has direction 0 = up
        adjacentTiles.add(grid.getTile(x,y-1));
        adjacentTiles.add(grid.getTile(x+1,y));
        adjacentTiles.add(grid.getTile(x,y+1));
        adjacentTiles.add(grid.getTile(x-1,y));
        int i=0;
        int direction = 0;
        //Tracks if the ant can see a pheromone
        boolean followTrail=false;
        while(!moved && i<4){
            JPanel adjacentTile=adjacentTiles.get(i);
            if(adjacentTile!= null) {
                Component tile=null;
                if(adjacentTile.getComponentCount()>0){
                    tile = adjacentTile.getComponent(0);
                }//Missing tile needs to be replaced
               else{
                    adjacentTile.add(new EmptyTile());
               }
                if (tile instanceof Food && !hasFood) {
                    moved = true;
                    move(i);
                    return;
                }
                //Will not move in the opposite direction as previous time to avoid getting stuck in a loop
                else if(tile instanceof Pheromone && i!=(previousDirection+2)%4){
                    followTrail=true;
                    direction=i;
                }
            }
            i++;
        }
        if(!moved){
            if(followTrail){
                leaveTrail=true;
                move(direction);
                leaveTrail=false;
            }
            else{
                direction=getDirection();
                move(direction);
            }

        }
    }

    private int getDirection() {
        Random rand = new Random();
            int direction = rand.nextInt(4);

            //If on right edge, move left
            if(x == grid.maxX() && direction==1){
                direction=3;
            }
            //If on bottom edge, move up
            else if(y ==grid.maxY() && direction==2){
                direction=0;
            }
            //If ant is in the bottom right corner tile, move up
        return direction;
    }

}
