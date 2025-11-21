public class PlayerAnt extends Ant {
    private Boolean playing=false;
    public PlayerAnt(GameMenu menu, int x, int y, int type) {
        super(menu, x, y,type);
        addActionListener(e -> {
            menu.changeAnt();
            setMainAnt();
            playing=true;
            // Give focus back to the gameMenu after being clicked
            menu.transferFocus();

        });
    }
    public Boolean getPlaying(){
        return playing;
    }
    public void setPlaying(Boolean playing){
        this.playing = playing;
    }
    public void collectFood(){
        menu.addFood();
    }
    public void eat(){
        hasFood=false;
        menu.decreaseFood();
    }
    public boolean fight(Ant opponent){
        if((type==1 && opponent instanceof Enemy)){
            menu.removeAnt(opponent);
            grid.setTile(opponent.getX(),opponent.getY(), new EmptyTile());
            return true;
        }
        return false;
    }
}
