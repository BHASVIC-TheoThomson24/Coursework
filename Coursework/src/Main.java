import java.util.ConcurrentModificationException;

public class Main {
    public static Game game=new Game();
    public static void main(String[] args){
        game.start();
        long lastTime=System.nanoTime();
        //noinspection InfiniteLoopStatement
        while(true){
            long time=System.nanoTime();
            long delta = time-lastTime;
            //10^8 nanoseconds = 100 ms
            if(delta >= 100000000){
                lastTime=time;
                GameMenu menu = (GameMenu) game.getFrames().get(2);
                GameplayGrid grid = menu.getGrid();
                    if(!game.isPaused() ){
                        try{
                            menu.tick();
                            grid.removeMissing();
                        }catch (ConcurrentModificationException ignored){}
                    }



            }

        }
    }
}