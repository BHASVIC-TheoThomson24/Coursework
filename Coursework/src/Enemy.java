import javax.swing.*;

public class Enemy extends Ant{
private static ImageIcon icon=new ImageIcon("./Enemy.png");
    public Enemy(GameMenu menu, int x, int y) {
        super(menu, x, y);
        setIcon(icon);
    }
    public void collectFood(){
        menu.addEnemyFood();
    }
    public void eat(){
        hasFood=false;
        menu.decreaseEnemyFood();
    }
}
