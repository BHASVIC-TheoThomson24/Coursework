import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;



public class Ant extends JButton {
    private int x;
    private int y;
    private final GameMenu menu;
    private final GameplayGrid grid;
    private Boolean playing=false;
    private Boolean hasFood=false;
    private Boolean leaveTrail=false;
    private int directionMoved=-1;
    private int previousDirection=-1;
    public Ant(GameMenu menu, int x, int y){
        this.x=x;
        this.y=y;
        this.menu=menu;
        this.grid=menu.getGrid();
        setIcon(new ImageIcon("./Ant.png"));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        //When ant is clicked
        addActionListener(e -> {
            menu.changeAnt();
            setMainAnt();
            playing=true;
           // Give focus back to the gameMenu after being clicked
            menu.transferFocus();

        });
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
            tile=panel.getComponent(0);
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
            if(directionMoved==-1){
                directionMoved=direction;
                previousDirection=direction;
            }
            previousDirection=directionMoved;
            directionMoved=direction;
            if(leaveTrail){
                try{
                    menu.setTile(x,y,new Pheromone(previousDirection+2,directionMoved));

                }catch(Error e){
                    return;
                }
            }
            else{
                menu.setTile(x,y,new JLabel(new ImageIcon("./EmptyTile.png")));

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
}
