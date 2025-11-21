public class PlayerAnt extends Ant {
    private Boolean playing=false;
    public PlayerAnt(GameMenu menu, int x, int y) {
        super(menu, x, y);
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
}
