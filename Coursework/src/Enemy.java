import javax.swing.*;

public class Enemy extends Ant{
private static final ImageIcon icon=new ImageIcon("./Enemy.png");
    public Enemy(GameMenu menu, int x, int y, int type) {
        super(menu, x, y, type);
        setIcon(icon);
    }
    public void collectFood(){
        menu.addEnemyFood();
    }
    public void eat(){
        hasFood=false;
        menu.decreaseEnemyFood();
    }
    public boolean fight(Ant opponent){
        if((type==1 && opponent instanceof PlayerAnt)){
            menu.removeAnt(opponent);
            grid.setTile(opponent.getX(),opponent.getY(), new EmptyTile());
            return true;
        }
        return false;

    }
}
