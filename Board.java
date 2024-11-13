import java.util.ArrayList;

public class Board 
{
	public static int W = 1;
    public static int B = -1;
    public static int EMPTY = 0;

    private int[][] gameBoard;

    //Variable containing who played last; whose turn resulted in this board
    //Even a new Board has lastLetterPlayed value; it denotes which player will play first
    private int lastPlayer;

    //Immediate move that lead to this board
    private Move lastMove;
	
	private final int dimension;

    private int [][] values;



    //constructor
	public Board() {
        this.lastMove = new Move();
        this.lastPlayer = B;
        this.gameBoard = new int[8][8];
        this.dimension = gameBoard.length;
        values = new int[8][8];
        
        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                this.gameBoard[row][col] = 0;
                

            }
        }
        this.gameBoard[3][3]=W;
        this.gameBoard[4][4]=W;
        this.gameBoard[3][4]=B;
        this.gameBoard[4][3]=B;

        
    
    }
	
	//copy constructor
    public Board(Board board) {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[8][8];
        this.dimension=board.dimension;
        values = new int[8][8];

        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                this.gameBoard[row][col] = board.gameBoard[row][col];
            }
        }
    }

    void setPoint(int[][] board, int row, int col, int value) {
                board[row][col] = value;
            }
        
    int getPoint(int[][] board, int row, int col) {
                return board[row][col];
            }

	public void print() {
        int k = 0;
        System.out.println("   0 1 2 3 4 5 6 7");
        System.out.println("  ******************");
        for (int i = 0; i < 8; i++) {
            System.out.print(k + " *");
            k++;
            for (int j = 0; j < 8; j++) {
                switch (this.gameBoard[i][j]) {
                    case 1:
                    System.out.print("W ");
                    break;
                    case -1:
                    System.out.print("B ");
                    break;
                    case 0:
                    System.out.print("- ");
                    break;
                }
            }
            System.out.println("*");
        }
        System.out.println("  ******************");
    }
	
	ArrayList<Board> getChildren(int value){
        ArrayList<Board> children = new ArrayList<>();
        for(int row = 0; row < this.dimension; row++){
            for(int col = 0; col < this.dimension; col++){
                if(this.isValidMove(row, col, value)){
                    Board child = new Board(this);
                    child.placePawn(row, col, value);
                    children.add(child);
                }
            }
        }
        return children;
    }
	
	public int evaluate () {
        int scoreW = 0;
        int scoreB = 0;

        //the more pawns on the board a player has, the better (weight = 1)
        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                if(this.gameBoard[row][col] == W) scoreW ++;
                else if (this.gameBoard[row][col] == B) scoreB ++;
            }
        }

        //the more corners of the board a player has, the better (weight = 3)
                if(this.gameBoard[0][0] == W) scoreW += 3;
                else if(this.gameBoard[0][0] == B) scoreB += 3;

                if(this.gameBoard[0][this.dimension-1] == W) scoreW += 3;
                else if (this.gameBoard[0][this.dimension-1] == B) scoreB += 3;

                if(this.gameBoard[this.dimension-1][0] == W) scoreW += 3;
                else if (this.gameBoard[this.dimension-1][0] == B) scoreB += 3;

                if(this.gameBoard[this.dimension-1][this.dimension-1] == W) scoreW += 3;
                if (this.gameBoard[this.dimension-1][this.dimension-1] == B) scoreB += 3;
        
        //the more borders of the board a player has, excluding the corners,
        //the better (weight = 2)
        //first row
        int i = 0;
        for(int j = 1; j < dimension-1; j++){
            if(this.gameBoard[i][j] == W) scoreW += 2;
            else if(this.gameBoard[i][j] == B) scoreB += 2;
        }
        //last row
        i = this.dimension - 1;
        for(int j = 1; j < dimension-1; j++){
            if(this.gameBoard[i][j] == W) scoreW += 2;
            else if(this.gameBoard[i][j] == B) scoreB += 2;
        }

        //first col
        int j = 0;
        for(i = 1; i < dimension-1; i++){
            if(this.gameBoard[i][j] == W) scoreW += 2;
            else if(this.gameBoard[i][j] == B) scoreB += 2;
        }

        //last col
        j = this.dimension - 1;
        for(i = 1; i < dimension-1; i++){
            if(this.gameBoard[i][j] == W) scoreW += 2;
            else if(this.gameBoard[i][j] == B) scoreB += 2;
        }

        return scoreW - scoreB;
    }
	
    
	public boolean isTerminal() {
        //the game finishes when neither player has any valid moves left, aka
        //when either the board gets filled or neither player can make a move that
        //that sandwiches at least one enemy pawn
        if(validMovesLeft(W)) return false;
        if(validMovesLeft(B)) return false;

        return true;
    }
	
	public Move getLastMove()
    {
        return this.lastMove;
    }

    public int getLastPlayer()
    {
        return this.lastPlayer;
    }

    public int[][] getGameBoard()
    {
        return this.gameBoard;
    }
	
	void setGameBoard(int[][] gameBoard)
    {
        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                this.gameBoard[row][col] = gameBoard[row][col];
            }
        }
    }
    
    void setBoard(int row, int col, int value) {
        this.gameBoard[row][col] = value;
    }
    
    void setLastMove(int row, int col, int value)
    {
        this.lastMove.setRow(row);
        this.lastMove.setCol(col);
        this.lastMove.setValue(value);
    }

    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }

    boolean isValidMove(int row, int col, int value ) {
        if(!checkBorders(row, col)) return false;

        if (getGameBoard()[row][col] != EMPTY) return false;

        if(checkHorizontal(row, col, value) || /*checkDiagonal(row, col, value) ||*/ checkVertical(row, col, value)) return true;
        
        return false;

       
    }

    boolean validMovesLeft(int value) {
        for(int i = 0; i < this.dimension; i++){
            for(int j = 0; j < this.dimension; j++) {
                if(isValidMove(i, j, value)) return true;
            }
        }
        return false;
    }

    boolean checkBorders(int row, int col) {
         if (row > this.dimension || row < 0 ||col > this.dimension || col < 0) return false;
    
         return true;
        }

    //check if there is a valid horizontal move and keep the coordinates of the enemy pawns in an array
    boolean checkHorizontal(int row, int col, int value) {
        //check right 
        boolean check = false;
        int count = 0;
        for(int i = (col+1); i < this.dimension; i++ ) {

            //checks if there is at least one enemy pawn between two frienldy pawns
            if(getGameBoard()[row][i] == (-value)) {
                count++;
                //keep the coordinates of the enemy pawns in an array
                setPoint(values,row,i,-value);
                continue;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[row][i] == value) && count > 0) check = true;
            //if the move turns out to be invalid, remove the coordinates of the enemy pawns from the array
            else if(getGameBoard()[row][i] == EMPTY) {
                for (int j = col; j< i; j++){
                    setPoint(values, row, j, EMPTY);
                }
                break;
            }
            
        
            
        }

        //check left
        count = 0;
        for(int i = (col-1); i >= 0; i-- ) {

            if(getGameBoard()[row][i] == (-value)) {
                count++;
                setPoint(values,row,i,-value);
                continue;
            }    
            
            //if at least one value is true, it returns true
            if((getGameBoard()[row][i] == value) && count > 0) return (check || true);
            else if(getGameBoard()[row][i] == EMPTY) {
                for (int j = col; j>i; j--){
                        setPoint(values, row, j, EMPTY);
                    }
                    break;
                }      
                
        }
        return (check || false);
    
    }

    boolean checkVertical(int row, int col, int value) {
        //check bottom
        boolean check = false;
        int count = 0;
        for(int i = (row+1); i < this.dimension; i++ ) {

            //checks if there is at least one enemy pawn between two frienldy pawns
            if(getGameBoard()[i][col] == (-value)) {
                count++;
                setPoint(values, i, col, -value);
                continue;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[i][col] == value) && count > 0) check = true;
            else if(getGameBoard()[i][col] == EMPTY) {
                for (int j = row; j<i; j++){
                    setPoint(values, j, col, EMPTY);
                }
                break;
            }
            
        }

        //check up
        count = 0;
        for(int i = (row-1); i >= 0; i-- ) {

            if(getGameBoard()[i][col] == (-value)) {
                count++;
                setPoint(values, i, col, -value);
                continue;
            }    
            //if at least one value is true, it returns true
            if((getGameBoard()[i][col] == value) && count > 0) return (check || true);
            else if(getGameBoard()[i][col] == EMPTY) {
                for (int j = row; j>i; j--){
                        setPoint(values, j, col, EMPTY);
                    }
                    break;
                }        

        }
        return (check || false);
    }

    boolean checkDiagonal(int row, int col, int value) {
        int count = 0;
        boolean check = false;
        //check bottom-right
        for(int i = (row+1); i < this.dimension; i++ ) {
            for(int j = (col+i); j < this.dimension;) {
                
                //checks if there is at least one enemy pawn between two frienldy pawns
                if(getGameBoard()[i][j] == (-value)) {
                count++;
                setPoint(values, i, j, -value);
                break;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[i][j] == value) && count > 0) check = true;
            else if(getGameBoard()[i][j] == EMPTY) {
                //horizontal, constant row
                for (int k = row; k>=j; k--){
                    //vertical, constant col
                    for (int l = col; l>=i; l--)
                    setPoint(values, j, i, EMPTY);
                }
                break;
            }
            break;
            }
        }

        //check bottom-left
        count = 0;

        for(int i = (row+1); i < this.dimension; i++) {
            for(int j = (col-i); j >=0;) {
                
                //checks if there is at least one enemy pawn between two frienldy pawns
                if(getGameBoard()[i][j] == (-value)) {
                count++;
                setPoint(values, i, j, -value);
                break;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[i][j] == value) && count > 0) check = true;
            else if(getGameBoard()[i][j] == EMPTY) {
                //horizontal, constant row
                for (int k = row; k<=j; k++){
                    //vertical, constant col
                    for (int l = col; l>=i; l--)
                    setPoint(values, j, k, EMPTY);
                }
                break;
            }
            break;
            }
        }

        
        //check top-right
        count = 0;

        for(int i = (row-1); i >=0; i-- ) {
            for(int j = (col+i); j < this.dimension;) {
                
                //checks if there is at least one enemy pawn between two frienldy pawns
                if(getGameBoard()[i][j] == (-value)) {
                count++;
                setPoint(values, i, j, -value);
                break;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[i][j] == value) && count > 0) check = true;
            else if(getGameBoard()[i][j] == EMPTY) {
                //horizontal, constant row
                for (int k = row; k>i; k--){
                    //vertical, constant col
                    for (int l = col; l<j; l++)
                    setPoint(values, j, l, EMPTY);
                }
                break;
            }
            break;
            }
        }
        
        //check top-left
        count = 0;

        for(int i = (row-1); i >0; i-- ) {
            for(int j = (col-i); j >0;) {
                
                //checks if there is at least one enemy pawn between two frienldy pawns
                if(getGameBoard()[i][j] == (-value)) {
                count++;
                setPoint(values, i, j, -value);
                break;
            }    

            //if at least one enemy pawn is sandwiched between two friendly pawns, the move is valid
            if((getGameBoard()[i][j] == value) && count > 0) check = true;
            else if(getGameBoard()[i][j] == EMPTY) {
                //horizontal, constant row
                for (int k = row; k<i; k--){
                    //vertical, constant col
                    for (int l = row; l<j; l--)
                    setPoint(values, k, j, EMPTY);
                }
                break;
            }
            break;
            }
        }
        
        return (check || false);
    }

	//mhpws prepei na kanei return to getGameBoard()? alla meta sth 2h grammh ti tha kanei return?
    boolean placePawn(int row, int col, int value) {
        if(!isValidMove(row, col, value)) return false;


        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values.length; j++) {
                if(values[i][j] == -value) 
                setBoard(i, j, value);
            }
        }
        setPoint(getGameBoard(), row,  col, value);
        this.lastMove = new Move(row, col);
        this.lastPlayer = value;

        //emptying the values board so that it will be empty for the next player to play
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values.length; j++) {
                setPoint(values, i, j, EMPTY);
            }
        }    

        return true;
    }
    
	
}