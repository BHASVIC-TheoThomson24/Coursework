public class playerAnt extends Ant {
    public playerAnt(GameMenu menu, int x, int y) {
        super(menu, x, y);
        addActionListener(e -> {
            menu.changeAnt();
            setMainAnt();
            playing=true;
            // Give focus back to the gameMenu after being clicked
            menu.transferFocus();

        });
    }
}
