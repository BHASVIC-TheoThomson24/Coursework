import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ant extends JButton {
    private int x=0;
    private int y=0;
    private GameMenu menu;
    Boolean playing=false;
    public Ant(){
        setIcon(new ImageIcon("./Ant.png"));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMenu();
                menu.changeAnt();
                playing=true;
               // Give focus back to the gameMenu after being clicked
                Main.game.getFrames().get(2).transferFocus();

            }
        });
    }
    //0=up, 1=right, 2=down, 3=left
    public void move(int direction){
       int dx=0;
       int dy=0;
       //increasing y moves down to next row
        switch(direction){
            case 0: dx=0; dy=-1;
            break;
            case 1: dx=1; dy=0;
            break;
            case 2: dx=0; dy=1;
            break;
            case 3: dx=-1; dy=0;
            break;
            default:
        }
        getMenu();

        Component tile = menu.getTile(x+dx,y+dy);
        //Cannot move to a space occupied by another ant
        if(tile instanceof Ant){
            return;
        }
        if(tile instanceof Food){
            menu.addFood();
        }
        if(x+dx>=0 && x+dx<=9 && y+dy>=0 && y+dy<=9){
            menu.setTile(x,y,new JLabel(new ImageIcon("./EmptyTile.png")));
            menu.setTile(x+dx,y+dy,this);
            menu.repaint();
        }
    }
    public void setCoordinates(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Boolean getPlaying(){
        return playing;
    }
    public void setPlaying(Boolean playing){
        this.playing = playing;
    }
    public void getMenu(){
        menu= (GameMenu) (getParent().getParent().getParent().getParent().getParent().getParent().getParent());
    }
}
