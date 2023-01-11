import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Stack;

//implement stack for AI Agent

public class AIImp extends JFrame{
    Random random;

    public AIImp(){
        random = new Random();
    }

    public class Move randomMoves(Stack possible){
        assert random != null;
        int moveNum = random.nextInt(possible.size());
        for(int e = 0; e<(possible.size()) - (moveNum);e++){
            Move moved = (Move) possible.pop();
        }
        Move chosenMove = (Move) possible.pop();
        return chosenMove;
    }

    public Move nextBest(Stack possible){
        Move getMove;
        Move chosenMove = new Move();
        Stack move = new Stack();

        Stack backup = (Stack) possible.clone();

        while (!possible.isEmpty()){
            getMove = (Move) possible.pop();
            Place p1 = (Place) getMove.getStart();
            Place p2 = (Place) getMove.getEnd();
            int x = p1.getxP();
            int y = p2.getyP();
            String pieceName = p1.getPieceName();

            if(getMove.isEmpty(){
                moves.add(getMove);
            }
                if(pieceName.contains("White"||"Black")){
                    move.add(getMove);
                }
            }
            if{
                int moveNum = random.nextInt(backup.size());
                for (int i =0; i<(backup.size()- (moveNum));i++){
                    backup.pop();
                }
            }
        }
    }
