import java.util.ConcurrentModificationException;

public class Main {
    public static Game game=new Game();
    public static final long startTime=System.nanoTime();
    private static boolean won=false;
    public static void main(String[] args){
        game.start();
        long lastTime=System.nanoTime();
        while(!won){
            long time=System.nanoTime();
            long delta = time-lastTime;
            //10^8 nanoseconds = 100 ms
            if(delta >= 100000000){
                lastTime=time;
                GameMenu menu = (GameMenu) game.getFrames().get(2);
                if(!game.isPaused() ){
                    try{
                        menu.tick();
                    }catch (ConcurrentModificationException ignored){}
                }
            }

        }
    }
    public static void win(){
        won=true;
    }
}