import java.util.Scanner;
public class Main {
    
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Player pc = new Player();

        System.out.println("Do you want to play first? (Y)es or (N)o?");
        String yesOrNo = scanner.nextLine();
        
        while(!yesOrNo.equals("Y") && !yesOrNo.equals("N")){
            System.out.println("Please select Y or N");
            yesOrNo = scanner.nextLine();
        }

        if(yesOrNo.equals("Y")) System.out.println("You have selected: "+ yesOrNo + ", you're playing black.");
        if(yesOrNo.equals("N")) System.out.println("You have selected: "+ yesOrNo + ", you're playing white.");

        System.out.println("Please select the maximum depth the computer will be searching.");
        Integer selectDepth = scanner.nextInt();
        while (selectDepth<0 || selectDepth >20){
            System.out.println("Please select a number between 1-20.");
            selectDepth = scanner.nextInt();
        }

        switch (yesOrNo){
            case "Y":
                //human player plays B, pc plays W
                pc.setPlayer(selectDepth,Board.W);
                break;
            case "N":
                //human player plays W, pc plays B
                pc.setPlayer(selectDepth,Board.B);
                break;
            default:
                break;
            }

        Board board1 = new Board();
        int color;
        board1.print();

        //while we're not in a terminal state of the board
        while(!board1.isTerminal()){
            //board1.print();
            //if player plays first aka has Black
            if(yesOrNo.equals("Y")){
                color = Board.B;
                System.out.println("Your turn, enter the [row number] of your desired move.");
                int row = scanner.nextInt();
                System.out.println("Enter the [column number] of your desired move.");
                int col = scanner.nextInt();
                while(!board1.isValidMove(row, col, color)){
                    System.out.println("Invalid move, try again.");
                    System.out.println("Your turn, enter the [row number] of your desired move.");
                    row = scanner.nextInt();
                    System.out.println("Enter the [column number] of your desired move.");
                    col = scanner.nextInt();
                }
                board1.placePawn(row, col, color);
                board1.print();

                
                Move moveW = pc.MiniMax(board1);
                board1.placePawn(moveW.getRow(), moveW.getCol(), Board.W);
                System.out.println("I will now place a pawn on row: " + moveW.getRow()+ " and col: "+ moveW.getCol());
                board1.print();

            }
            else{
                color = Board.W;

                Move moveB = pc.MiniMax(board1);
                board1.placePawn(moveB.getRow(), moveB.getCol(), Board.B);
                System.out.println("I will now place a pawn on row: " + moveB.getRow()+ " and col: "+ moveB.getCol());
                board1.print();

                System.out.println("Your turn, enter the [row number] of your desired move.");
                int row = scanner.nextInt();
                System.out.println("Enter the [column number] of your desired move.");
                int col = scanner.nextInt();
                while(!board1.isValidMove(row, col, color)){
                    System.out.println("Invalid move, try again.");
                    System.out.println("Your turn, enter the [row number] of your desired move.");
                    row = scanner.nextInt();
                    System.out.println("Enter the [column number] of your desired move.");
                    col = scanner.nextInt();
                }
                board1.placePawn(row, col, color);
                board1.print();
            }
            
        }


        scanner.close();
    }
        

}




    

