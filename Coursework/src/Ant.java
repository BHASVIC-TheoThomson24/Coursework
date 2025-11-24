import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Ant extends JButton {
    private static final ImageIcon icon = new ImageIcon("./Ant.png");
    protected int x;
    protected int y;
    protected final GameMenu menu;
    protected final GameplayGrid grid;
    protected Boolean hasFood=false;
    protected Boolean leaveTrail=false;
    protected int previousDirection=-1;
    protected int type;
    //0=Food gatherer, 1= fighter
    public Ant(GameMenu menu, int x, int y, int type){
        this.x=x;
        this.y=y;
        this.menu=menu;
        this.grid=menu.getGrid();
        this.type=type;
        setIcon(icon);
        setBorder(new EmptyBorder(0, 0, 0, 0));
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

        //Cannot move to a space occupied by another ant unless it is a fighter, and can remove enemies
        if(tile instanceof Ant){
            if(!fight((Ant) tile)){
                return;
            }
        }
        if(tile instanceof Food){
            if(!hasFood){
                hasFood=true;
                collectFood();
            }
            else{
                return;
            }
        }

        if(x+dx>=0 && y+dy>=0){
            //If it is first time moving, set both directions to be the same.
            if(previousDirection==-1){
                previousDirection=direction;
            }

            if(leaveTrail){
                try{
                    menu.setTile(x,y,new Pheromone(previousDirection+2,direction));

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
        previousDirection=direction;
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
    public void eat(){
    }
    public void toggleTrail(){
        leaveTrail=!leaveTrail;
    }
    public void setTrail(boolean b){
        leaveTrail=b;
    }
    public void tick(){
        //Ants are ticked on average once every 2 seconds, and have a 1/500 chance to die each tick, giving an average lifetime of 1000 seconds = 16 minutes, 40 seconds
        if(Math.random()<0.002){
            menu.removeAnt(this);
            grid.setTile(x,y,new EmptyTile());
            return;
        }
        //Gatherers have shorter average cooldown between picking up food, of 20 ticks= 2 seconds, compared to 5 seconds for fighter
        if(type==0 || (type==1 && Math.random()<0.4)){
            if(hasFood){
                hasFood=false;
                //20% chance for food to be deleted when it stops carrying, so that the player can increase food without new ants.
                if(Math.random()<0.2){
                    eat();
                }
            }
        }
        if(!(this instanceof PlayerAnt && ((PlayerAnt) this).getPlaying() )){
            //Ant accesses 4 adjacent tiles
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
            while(i<4){
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
            if (followTrail) {
                leaveTrail = true;
                move(direction);
                leaveTrail = false;
            } else {
                direction = getDirection();
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
    public void collectFood(){

    }
    //Override in each subclass, returns true if fight took place, false otherwise
    public boolean fight(Ant opponent){
        return false;
    }
    public int getType(){
        return type;
    }
}
