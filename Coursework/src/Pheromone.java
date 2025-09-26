import javax.swing.*;

public class Pheromone extends JLabel {
    public Pheromone(int edge1, int edge2) {
        super();
        ImageIcon image;
        //edge indexed from 0 to 3 clockwise from top. top=0, right=1, bottom=2, left=3
        edge1%=4;
        edge2%=4;
        //Swap the edges so that first edge is always lowest
        if(edge1==edge2){
            throw new Error("Pheromone edges equal");
        }
        if(edge1>edge2){
            int temp=edge1;
            edge1=edge2;
            edge2=temp;
        }
        //Edge1 is in range 0-2, edge2 can be 1-3, they are not equal

        //edge1==0 -> Pheromone starts at top
        if(edge1==0 && edge2==1){
            image=new ImageIcon("./PheromoneTopRight.png");
        }
        else if(edge1==0 && edge2==2){
            image=new ImageIcon("./Pheromone.png");
        }
        else if(edge1 == 0){
            image=new ImageIcon("./PheromoneTopLeft.png");
        }
        //edge1==1 -> Pheromone starts at right
        else if(edge1==1 && edge2==2){
            image=new ImageIcon("./PheromoneBottomRight.png");
        }
        else if(edge1 == 1){
            image=new ImageIcon("./PheromoneHorizontal.png");
        }
        //Only other possibility is edge 2 to 3, which is bottom to left
        else{
            image=new ImageIcon("./PheromoneBottomLeft.png");
        }
        setIcon(image);
    }
}
