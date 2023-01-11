public class Place {
    public int xP;
    public int yP;
    public String pieceName;

    public Place(int x, int y, String Name){
        xP = x;
        yP = y;
        pieceName = Name;
    }
    public Place(int x, int y){
        xP = x;
        yP = y;
        pieceName = "";
    }
    public int getxP(){
        return xP;
    }

    public int getyP(){
        return yP;
    }

    public String getPieceName() {
        return pieceName;
    }
}
