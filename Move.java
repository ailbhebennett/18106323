public class Move {
    Move start;
    Move end;

    public Move(Move x, Move y){
        start = x;
        end = y;
    }

    public Move(){
    }

    public Move getStart(){
        return start;
    }

    public Move getEnd(){
        return end;
    }
}
