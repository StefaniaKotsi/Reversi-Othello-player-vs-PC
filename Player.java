import java.util.ArrayList;
import java.util.Random;

public class Player 
{
	private int maxDepth;
    private int playerLetter;
	
	public Player() {}

    public Player(int maxDepth, int playerLetter)
    {
        this.maxDepth = maxDepth;
        this.playerLetter = playerLetter;
    }
	
    public void setPlayer(int depth, int letter){
        this.maxDepth = depth;
        this.playerLetter = letter;
    }

    
	public Move MiniMax(Board board) {
        if(playerLetter == Board.W)
        {
            //If W plays then it wants to maximize the heuristics value
            return max(new Board(board), 0);
        }
        else
        {
            //If the B plays then it wants to minimize the heuristics value
            return min(new Board(board), 0);
        }
    }

    //The max and min functions are called one after another until a max depth or terminal state is reached
    //We create a tree using backtracking DFS
    Move max(Board board, int depth)
    {
        Random r = new Random();
        //If MAX is called on a terminal state or after a maximum depth is reached,
        //then a heuristic is calculated on the state and the move returned
        if(board.isTerminal() || (depth == this.maxDepth)){
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }
        //The children-moves of the state are calculated
        ArrayList<Board> children = board.getChildren(Board.W);
        Move maxMove = new Move(Integer.MIN_VALUE); // put max node initially to smallest value.
        for(Board child: children)
        {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if(move.getValue() >= maxMove.getValue())
            {
                //If the heuristic has the save value then we randomly choose one of the two moves
                if((move.getValue()) == maxMove.getValue())
                {
                    if(r.nextInt(2) == 0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

	//Min works similarly to max
    Move min(Board board, int depth){
        Random r = new Random();
        if(board.isTerminal() || (depth == this.maxDepth)){
            return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
        }
        ArrayList<Board> children = board.getChildren(Board.B);
        Move minMove = new Move(Integer.MAX_VALUE);
        for(Board child: children){
            Move move = max(child, depth + 1);
            if(move.getValue() <= minMove.getValue()){
                if((move.getValue()) == minMove.getValue())
                {
                    if(r.nextInt(2) == 0)
                    {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }
    


}